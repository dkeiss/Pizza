interface IUser
{
    userId: number;
    firstName: string;
    lastName: string;
    userName: string;
    discount: number;
    admin: boolean;
    creationDate?: number;
    modificationDate?: number;
}




interface IOrder
{
    productId: number;
    productVariationId: number;
    userOrderAdditionals: IUserOrderAdditionals;
    number: number;
}

interface IUserOrderAdditionals extends Array<IUserOrderAdditional>{}

interface IUserOrderAdditional
{
    additionalId: number,
    additionalPriceId: number
}