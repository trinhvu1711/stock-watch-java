import apiClientService from "@/common/services/ApiClientService";
import { CapturePayment } from "../models/CapturePayment";
import { Payment } from "../models/Payment";
import { PaymentGetVm } from "../models/PaymentGetVm";

const baseUrl = "http://localhost:8070/api/v1";
export async function createPayment(
  payment: CapturePayment
): Promise<Payment | null> {
  const response = await apiClientService.post(
    `${baseUrl}/payments/capture`,
    JSON.stringify(payment)
  );
  if (response.status >= 200 && response.status < 300) {
    return await response.json();
  }
  throw new Error(response.statusText);
}

export async function getPaymentById(id: number): Promise<PaymentGetVm | null> {
  const response = await apiClientService.get(`${baseUrl}/payments/${id}`);
  if (response.status >= 200 && response.status < 300) return response.json();
  throw new Error(response.statusText);
}
