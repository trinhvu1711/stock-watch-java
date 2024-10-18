"use client";
import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import Link from "next/link";
import { OrderVm } from "@/components/order/models/OrderVm";
import { getOrderById } from "@/components/order/services/OrderService";
import { useRouter } from "next/navigation";
import { Payment } from "@/components/payment/models/Payment";
import { CapturePayment } from "@/components/payment/models/CapturePayment";
import { create } from "domain";
import { createPayment } from "@/components/payment/services/PaymentService";
type Props = {
  params: {
    id: string;
  };
};

export default function PaymentPage({ params }: Props) {
  const router = useRouter();
  const id = params.id;
  const [order, setOrder] = useState<OrderVm>();
  const [isLoading, setLoading] = useState(true);
  const [paymentMethod, setPaymentMethod] = useState("BANKING");
  const [cardDetails, setCardDetails] = useState({
    cardNumber: "",
    expiryDate: "",
    cvv: "",
  });

  useEffect(() => {
    setLoading(true);
    if (id) {
      getOrderById(+id)
        .then((res) => {
          console.log("🚀 ~ .then ~ res:", res);
          setOrder(res);
          setLoading(false);
        })
        .catch((err) => {
          console.log("🚀 ~ useEffect ~ err:", err.message);
        });
    }
  }, [id]);

  if (isLoading) return <p>Loading...</p>;

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setCardDetails((prevDetails) => ({ ...prevDetails, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    // Here you would typically process the payment
    if (!order) {
      console.error("Order is undefined");
      return;
    }
    console.log("🚀 ~ handleSubmit ~ order:", order);
    const capturePayment: CapturePayment = {
      orderId: order!.id!,
      checkOutId: order.checkoutId,
      amount: order.totalPrice,
      paymentMethod: paymentMethod,
      paymentStatus: "COMPLETED",
      failureMessage: "",
    };
    console.log("🚀 ~ handleSubmit ~ capturePayment:", capturePayment);

    const payment = await createPayment(capturePayment);

    console.log("Payment processed:", { paymentMethod, cardDetails });
    // Redirect to order confirmation page
    router.push(`/payment/confirmation/${payment!.paymentId}`);
  };

  return (
    <div className="container mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle>Payment</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit}>
            <div className="grid gap-4">
              <div className="grid gap-2">
                <Label>Payment Method</Label>
                <RadioGroup
                  value={paymentMethod}
                  onValueChange={setPaymentMethod}
                >
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="BANKING" id="banking" />
                    <Label htmlFor="banking">Banking</Label>
                  </div>
                  <div className="flex items-center space-x-2">
                    <RadioGroupItem value="PAYPAL" id="paypal" />
                    <Label htmlFor="paypal">PayPal</Label>
                  </div>
                </RadioGroup>
              </div>
              {paymentMethod === "banking" && (
                <>
                  <div className="grid gap-2">
                    <Label htmlFor="cardNumber">Card Number</Label>
                    <Input
                      id="cardNumber"
                      name="cardNumber"
                      value={cardDetails.cardNumber}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="grid gap-2">
                    <Label htmlFor="expiryDate">Expiry Date</Label>
                    <Input
                      id="expiryDate"
                      name="expiryDate"
                      placeholder="MM/YY"
                      value={cardDetails.expiryDate}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                  <div className="grid gap-2">
                    <Label htmlFor="cvv">CVV</Label>
                    <Input
                      id="cvv"
                      name="cvv"
                      value={cardDetails.cvv}
                      onChange={handleInputChange}
                      required
                    />
                  </div>
                </>
              )}
            </div>
          </form>
        </CardContent>
        <CardFooter className="flex justify-between">
          <Link href="/checkout">
            <Button variant="outline">Back to Checkout</Button>
          </Link>
          <Button type="submit" onClick={handleSubmit}>
            Complete Purchase
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}
