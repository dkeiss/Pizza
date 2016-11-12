interface IErrorResponse
{
    timestamp: number;
    status: number;
    error: string;
    exception: string;
    message: string;
    path: string;
}

interface IBulkOrder
{
    bulkOrderId: number;
    catalogId: number;
    name: string;
    activeUntil: number;
    creationDate?: number;
    modificationDate?: number;
    isActive?: boolean;
}

interface IDeliveryData
{
    deliveryServiceId: number;
    deliveryServiceName: string;
    firstName: string;
    lastName: string;
    street: string;
    streetNumber: string;
    postalCode: string;
    town: string;
    /*phone: string;
    eMail: string;
    additionalInformation?: string;*/
}