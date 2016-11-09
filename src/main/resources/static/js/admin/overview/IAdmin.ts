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
    informationId: number;
    company: string;
    firstName: string;
    lastName: string;
    street: string;
    number: string;
    zipCode: number;
    city: string;
    phone: string;
    eMail: string;
    additionalInformation?: string;
}