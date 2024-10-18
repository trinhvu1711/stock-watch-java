"use client";

import { OrderVm } from "@/components/order/models/OrderVm";
import { getOrderById } from "@/components/order/services/OrderService";
import { PaymentGetVm } from "@/components/payment/models/PaymentGetVm";
import { getPaymentById } from "@/components/payment/services/PaymentService";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { CheckCircle2 } from "lucide-react";
import Link from "next/link";
import { useEffect, useState } from "react";

type Props = {
  params: {
    id: string;
  };
};

export default function PaymentConfirmationPage({ params }: Props) {
  const id = params.id;
  const [payment, setPayment] = useState<PaymentGetVm>();
  const [order, setOrder] = useState<OrderVm>();
  const [isLoading, setLoading] = useState(true);

  const fetchOrder = async (id: number) => {
    getOrderById(+id)
      .then((res) => {
        console.log("🚀 ~ .then ~ res:", res);
        setOrder(res);
        setLoading(false);
      })
      .catch((err) => {
        console.log("🚀 ~ useEffect ~ err:", err.message);
      });
  };

  useEffect(() => {
    setLoading(true);
    if (id) {
      getPaymentById(+id)
        .then((res) => {
          console.log("🚀 ~ .then ~ res:", res);
          setPayment(res);
          setLoading(false);
        })
        .catch((err) => {
          console.log("🚀 ~ useEffect ~ err:", err.message);
        });
    }
  }, [id]);

  useEffect(() => {
    setLoading(true);
    if (payment?.orderId) {
      getOrderById(payment?.orderId)
        .then((res) => {
          console.log("🚀 ~ .then ~ res:", res);
          setOrder(res);
          setLoading(false);
        })
        .catch((err) => {
          console.log("🚀 ~ useEffect ~ err:", err.message);
        });
    }
  }, [payment?.orderId]);

  if (isLoading) return <p>Loading...</p>;

  return (
    <div className="container mx-auto py-8">
      <Card className="max-w-3xl mx-auto">
        <CardHeader className="text-center">
          <div className="flex justify-center mb-4">
            <CheckCircle2 className="h-16 w-16 text-green-500" />
          </div>
          <CardTitle className="text-2xl">Payment Confirmed</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm font-medium text-gray-500">
                  Checkout Number
                </p>
                <p className="text-lg font-semibold">{payment?.checkOutId}</p>
              </div>
              <div>
                <p className="text-sm font-medium text-gray-500">Date</p>
                <p className="text-lg">
                  {payment?.createdOn
                    ? new Date(payment.createdOn).toLocaleDateString()
                    : "N/A"}
                </p>
              </div>
              <div>
                <p className="text-sm font-medium text-gray-500">
                  Total Amount
                </p>
                <p className="text-lg font-semibold">
                  ${payment?.amount.toFixed(2)}
                </p>
              </div>
              <div>
                <p className="text-sm font-medium text-gray-500">
                  Payment Method
                </p>
                <p className="text-lg">{payment?.paymentMethod}</p>
              </div>
            </div>
            <div className="mt-6">
              <h3 className="text-lg font-semibold mb-2">Order Summary</h3>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Symbol</TableHead>
                    <TableHead>Name</TableHead>
                    <TableHead>Quantity</TableHead>
                    <TableHead>Price</TableHead>
                    <TableHead>Total</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {order?.orderItemVms.map((item) => (
                    <TableRow key={item.stockSymbol}>
                      <TableCell className="font-medium">
                        {item.stockSymbol}
                      </TableCell>
                      <TableCell>{item.stockName}</TableCell>
                      <TableCell>{item.quantity}</TableCell>
                      <TableCell>${item.stockPrice.toFixed(2)}</TableCell>
                      <TableCell>
                        ${(item.quantity * item.stockPrice).toFixed(2)}
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </div>
          </div>
        </CardContent>
        <CardFooter className="flex justify-center space-x-4">
          <Link href="/">
            <Button variant="outline">Back to Dashboard</Button>
          </Link>
          <Link href="/order">
            <Button>View Orders</Button>
          </Link>
        </CardFooter>
      </Card>
    </div>
  );
}
