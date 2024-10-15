import apiClientService from "@/common/services/ApiClientService";
import { EOrderStatus } from "../models/EOrderStatus";
import { OrderGetVm } from "../models/OrderGetVm";
import { OrderVm } from "../models/OrderVm";
import { CheckoutVm } from "../models/CheckoutVm";
import { CheckoutPostVm } from "../models/CheckoutPostVm";

const baseUrl = "http://localhost:8222/api/v1";

export async function createOrder(order: OrderVm): Promise<OrderVm | null> {
  const response = await apiClientService.post(
    `${baseUrl}/orders`,
    JSON.stringify(order)
  );
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  throw new Error(response.statusText);
}

export async function getMyOrders(
  stockName: string,
  orderStatus: EOrderStatus | null
): Promise<OrderGetVm[]> {
  const res = await apiClientService.get(
    `${baseUrl}/order/my-orders?stockName=${stockName}&orderStatus=${
      orderStatus ?? ""
    }`
  );
  if (res.status >= 200 && res.status < 300) return res.json();
  throw res;
}

export async function createCheckout(
  checkout: CheckoutPostVm
): Promise<CheckoutVm | null> {
  const response = await apiClientService.post(
    `${baseUrl}/checkouts`,
    JSON.stringify(checkout)
  );
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  throw new Error(response.statusText);
}

export async function getCheckoutById(id: string) {
  const response = await apiClientService.get(`${baseUrl}/checkouts/${id}`);
  if (response.status >= 200 && response.status < 300) return response.json();
  throw new Error(response.statusText);
}
