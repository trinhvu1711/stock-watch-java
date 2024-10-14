"use client";
import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import Link from "next/link";

// Mock data for cart items
const initialCartItems = [
  { symbol: "AAPL", name: "Apple Inc.", quantity: 2, price: 150.25 },
  { symbol: "GOOGL", name: "Alphabet Inc.", quantity: 1, price: 2750.8 },
  { symbol: "MSFT", name: "Microsoft Corporation", quantity: 3, price: 305.15 },
];

export default function CartPage() {
  const [cartItems, setCartItems] = useState(initialCartItems);

  const updateQuantity = (symbol: string, newQuantity: number) => {
    setCartItems(
      cartItems.map((item) =>
        item.symbol === symbol ? { ...item, quantity: newQuantity } : item
      )
    );
  };

  const removeItem = (symbol: string) => {
    setCartItems(cartItems.filter((item) => item.symbol !== symbol));
  };

  const total = cartItems.reduce(
    (sum, item) => sum + item.quantity * item.price,
    0
  );

  return (
    <div className="container mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle>Your Cart</CardTitle>
        </CardHeader>
        <CardContent>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Symbol</TableHead>
                <TableHead>Name</TableHead>
                <TableHead>Quantity</TableHead>
                <TableHead>Price</TableHead>
                <TableHead>Total</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {cartItems.map((item) => (
                <TableRow key={item.symbol}>
                  <TableCell>{item.symbol}</TableCell>
                  <TableCell>{item.name}</TableCell>
                  <TableCell>
                    <Input
                      type="number"
                      value={item.quantity}
                      onChange={(e) =>
                        updateQuantity(item.symbol, parseInt(e.target.value))
                      }
                      min="1"
                      className="w-20"
                    />
                  </TableCell>
                  <TableCell>${item.price.toFixed(2)}</TableCell>
                  <TableCell>
                    ${(item.quantity * item.price).toFixed(2)}
                  </TableCell>
                  <TableCell>
                    <Button
                      variant="destructive"
                      size="sm"
                      onClick={() => removeItem(item.symbol)}
                    >
                      Remove
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
        <CardFooter className="flex justify-between">
          <div className="text-2xl font-bold">Total: ${total.toFixed(2)}</div>
          <Link href="/checkout">
            <Button>Proceed to Checkout</Button>
          </Link>
        </CardFooter>
      </Card>
    </div>
  );
}
