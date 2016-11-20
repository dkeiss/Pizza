interface IAdditions extends Array<IAddition>{}

interface IAddition
{
    additionalCategoryId: number;
    name: string;
    duty: boolean;
    productIds: number[];
    additionals: IAdditionals;
    creationDate?: number;
}



interface IAdditionals extends Array<IAdditional>{}

interface IAdditional
{
    additionalId: number;
    description: string;
    additionalPrices: IAdditionalPrices;
    creationDate?: number;
}



interface IAdditionalPrices extends Array<IAdditionalPrice>{}

interface IAdditionalPrice
{
    additionalPriceId: number;
    name: string;
    price: number;
    creationDate?: number;
}