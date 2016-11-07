
interface IProductAdditionInfo{
    additionalId: number;
    description: string;
}


interface IProductAdditions extends Array<IProductAdditionInfo>{}

interface IUserOrder
{
    userOrderId: number;
    userId: number;
    firstName: string;
    lastName: string;
    productId: number;
    productName: string;
    productVariationId: number;
    productVariationName: string;
    additionals: IProductAdditions;
    sum: number;
    paid: boolean;
}

interface IUserOrderPaid {
    paid: boolean;
}
