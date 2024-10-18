"use client";

import { OrderVm } from "@/components/order/models/OrderVm";
import { getOrderById } from "@/components/order/services/OrderService";
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
import Link from "next/link";
import { useEffect, useState } from "react";

type Props = {
  params: {
    id: string;
  };
};

export default function OrderPage({ params }: Props) {
  const id = params.id;
  const [order, setOrder] = useState<OrderVm>();
  const [isLoading, setLoading] = useState(true);

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
  return (
    <div className="container mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle>Order Confirmation</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4">
            <div>
              <h3 className="font-semibold">Order ID: {order?.id}</h3>
              {/* <p>Date: {order.}</p> */}
              <p>Status: {order?.orderStatus}</p>
            </div>
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
                    <TableCell>{item.stockSymbol}</TableCell>
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
            <div className="text-right text-2xl font-bold">
              Total: ${order?.totalPrice.toFixed(2)}
            </div>
          </div>
        </CardContent>
        <CardFooter className="flex justify-between">
          <Link href="/">
            <Button>Back to Dashboard</Button>
          </Link>
          <Link href={`/payment/${order!.id}`}>
            <Button>Create Payment</Button>
          </Link>
        </CardFooter>
      </Card>
    </div>
  );
}
