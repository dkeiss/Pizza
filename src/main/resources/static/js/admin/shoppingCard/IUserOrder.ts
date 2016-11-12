
interface IProductAdditionInfo{
    userOrderAdditionalId: number;
    userOrderId: number;
    additionalId: number;
    additionalDescription: string;
    additionalPriceId: number;
    additionalPriceName: string;
}


interface IProductAdditions extends Array<IProductAdditionInfo>{}

interface IUserOrder
{
    userOrderId: number;
    userId: number;
    productId: number;
    productVariationId: number;
    number: number;
    userOrderAdditionals: IProductAdditions;
    firstName: string;
    lastName: string;
    productName: string;
    productVariationName: string;
    sum: number;
    paid: boolean;
}

interface IUserOrderPaid {
    paid: boolean;
}