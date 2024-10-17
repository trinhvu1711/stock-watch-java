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
import Link from "next/link";
import { useRouter } from "next/navigation";
import {
  createOrder,
  getCheckoutById,
} from "@/components/order/services/OrderService";
import { CheckoutVm } from "@/components/order/models/CheckoutVm";
import { OrderItemGetVm } from "@/components/order/models/OrderItemGetVm";
import { OrderItemVm } from "@/components/order/models/OrderItemVm";
import { OrderGetVm } from "@/components/order/models/OrderGetVm";
import { OrderVm } from "@/components/order/models/OrderVm";

type Props = {
  params: {
    id: string;
  };
};

export default function CheckoutPage({ params }: Props) {
  const router = useRouter();
  const [checkout, setCheckout] = useState<CheckoutVm>();
  const [orderItem, setOrderItem] = useState<OrderItemVm[]>([]);

  const id = params.id;
  const [formData, setFormData] = useState({
    email: "",
    note: "",
  });

  const fetchCechkout = async () => {
    await getCheckoutById(id)
      .then((res) => {
        if (res) {
          setCheckout(res);
          console.log("🚀 ~ CheckoutPage ~ checkout:", res);
          const newItem: OrderItemVm[] = [];
          res.checkoutItemSet.forEach((item) => {
            newItem.push({
              stockId: item.id,
              stockName: item.stockName,
              stockSymbol: item.stockSymbol,
              stockPrice: item.price,
              quantity: item.quantity,
            });
          });
          setOrderItem(newItem);
          console.log("🚀 ~ .then ~ newItem:", newItem);
        }
      })

      .catch((err) => {
        if (err.status === 404) {
          router.push("/cart");
        }
        if (err.status === 401) {
          router.push("/login");
        }
      });
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({ ...prevData, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    // Here you would typically send the form data to your backend
    try {
      const numberItem = orderItem.reduce(
        (result, item) => result + item.quantity,
        0
      );
      const totalPrice = orderItem.reduce(
        (result, item) => result + item.quantity * item.stockPrice,
        0
      );
      const newOrder: OrderVm = {
        email: formData.email,
        note: formData.note,
        orderStatus: "PENDING", // or appropriate default value
        paymentStatus: "PENDING",
        totalPrice: totalPrice,
        numberItem: numberItem,
        checkoutId: id,
        orderItem: orderItem,
      };
      console.log("🚀 ~ handleSubmit ~ newOrder:", newOrder);

      const createdOrder = createOrder(newOrder);
      console.log("Order created:", createdOrder);

      // Redirect to payment or order summary page
      router.push(`/order/${createdOrder.id}`);
    } catch (error) {
      console.error("Order creation error:", error);
    }
  };

  useEffect(() => {
    fetchCechkout();
  }, [id]);

  return (
    <div className="container mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle>Checkout</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit}>
            <div className="grid gap-4">
              <div className="grid gap-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  id="email"
                  name="email"
                  type="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  required
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="note">Note</Label>
                <Input
                  id="note"
                  name="note"
                  value={formData.note}
                  onChange={handleInputChange}
                  required
                />
              </div>
            </div>
          </form>
        </CardContent>
        <CardFooter className="flex justify-between">
          <Link href="/cart">
            <Button variant="outline">Back to Cart</Button>
          </Link>
          <Button type="submit" onClick={handleSubmit}>
            Create Order
          </Button>
        </CardFooter>
      </Card>
    </div>
  );
}
