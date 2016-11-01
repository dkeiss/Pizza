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