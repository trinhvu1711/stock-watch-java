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
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Badge } from "@/components/ui/badge";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Label } from "@/components/ui/label";

// Mock data for orders
const initialOrders = [
  {
    id: "ORD-001",
    date: "2023-06-15",
    symbol: "AAPL",
    quantity: 10,
    price: 150.25,
    status: "Completed",
  },
  {
    id: "ORD-002",
    date: "2023-06-16",
    symbol: "GOOGL",
    quantity: 5,
    price: 2750.8,
    status: "Pending",
  },
  {
    id: "ORD-003",
    date: "2023-06-17",
    symbol: "MSFT",
    quantity: 15,
    price: 305.15,
    status: "Completed",
  },
  {
    id: "ORD-004",
    date: "2023-06-18",
    symbol: "AMZN",
    quantity: 8,
    price: 3380.5,
    status: "Cancelled",
  },
  {
    id: "ORD-005",
    date: "2023-06-19",
    symbol: "TSLA",
    quantity: 12,
    price: 650.75,
    status: "Pending",
  },
];

export default function OrderManagementPage() {
  const [orders, setOrders] = useState(initialOrders);
  const [filterSymbol, setFilterSymbol] = useState("All");
  const [filterStatus, setFilterStatus] = useState("All");
  const [selectedOrder, setSelectedOrder] = useState(null);

  const filteredOrders = orders.filter(
    (order) =>
      (filterSymbol === "All" ||
        order.symbol.toLowerCase().includes(filterSymbol.toLowerCase())) &&
      (filterStatus === "All" || order.status === filterStatus)
  );

  const handleCancelOrder = (orderId) => {
    setOrders(
      orders.map((order) =>
        order.id === orderId ? { ...order, status: "Cancelled" } : order
      )
    );
  };

  const handleUpdateOrder = (orderId, newQuantity) => {
    setOrders(
      orders.map((order) =>
        order.id === orderId ? { ...order, quantity: newQuantity } : order
      )
    );
    setSelectedOrder(null);
  };

  return (
    <div className="container mx-auto py-8">
      <Card>
        <CardHeader>
          <CardTitle>Order Management</CardTitle>
        </CardHeader>
        <CardContent>
          <div className="flex justify-between mb-4">
            <Input
              placeholder="Filter by symbol"
              value={filterSymbol}
              onChange={(e) => setFilterSymbol(e.target.value)}
              className="w-64"
            />
            <Select value={filterStatus} onValueChange={setFilterStatus}>
              <SelectTrigger className="w-64">
                <SelectValue placeholder="Filter by status" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="All">All Statuses</SelectItem>
                <SelectItem value="Completed">Completed</SelectItem>
                <SelectItem value="Pending">Pending</SelectItem>
                <SelectItem value="Cancelled">Cancelled</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Order ID</TableHead>
                <TableHead>Date</TableHead>
                <TableHead>Symbol</TableHead>
                <TableHead>Quantity</TableHead>
                <TableHead>Price</TableHead>
                <TableHead>Total</TableHead>
                <TableHead>Status</TableHead>
                <TableHead>Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {filteredOrders.map((order) => (
                <TableRow key={order.id}>
                  <TableCell>{order.id}</TableCell>
                  <TableCell>{order.date}</TableCell>
                  <TableCell>{order.symbol}</TableCell>
                  <TableCell>{order.quantity}</TableCell>
                  <TableCell>${order.price.toFixed(2)}</TableCell>
                  <TableCell>
                    ${(order.quantity * order.price).toFixed(2)}
                  </TableCell>
                  <TableCell>
                    <Badge
                      variant={
                        order.status === "Completed"
                          ? "default"
                          : order.status === "Pending"
                          ? "secondary"
                          : "destructive"
                      }
                    >
                      {order.status}
                    </Badge>
                  </TableCell>
                  <TableCell>
                    <Dialog>
                      <DialogTrigger asChild>
                        <Button
                          variant="outline"
                          size="sm"
                          onClick={() => setSelectedOrder(order)}
                        >
                          Edit
                        </Button>
                      </DialogTrigger>
                      <DialogContent>
                        <DialogHeader>
                          <DialogTitle>Edit Order {order.id}</DialogTitle>
                          <DialogDescription>
                            Update the quantity for this order.
                          </DialogDescription>
                        </DialogHeader>
                        <div className="grid gap-4 py-4">
                          <div className="grid grid-cols-4 items-center gap-4">
                            <Label htmlFor="quantity" className="text-right">
                              Quantity
                            </Label>
                            <Input
                              id="quantity"
                              type="number"
                              className="col-span-3"
                              value={selectedOrder?.quantity}
                              onChange={(e) =>
                                setSelectedOrder({
                                  ...selectedOrder,
                                  quantity: parseInt(e.target.value),
                                })
                              }
                            />
                          </div>
                        </div>
                        <DialogFooter>
                          <Button
                            onClick={() =>
                              handleUpdateOrder(
                                order.id,
                                selectedOrder.quantity
                              )
                            }
                          >
                            Save changes
                          </Button>
                        </DialogFooter>
                      </DialogContent>
                    </Dialog>
                    {order.status === "Pending" && (
                      <Button
                        variant="destructive"
                        size="sm"
                        className="ml-2"
                        onClick={() => handleCancelOrder(order.id)}
                      >
                        Cancel
                      </Button>
                    )}
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}
