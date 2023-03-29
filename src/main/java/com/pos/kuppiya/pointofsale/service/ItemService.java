package com.pos.kuppiya.pointofsale.service;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.paginated.PaginatedResponseItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemUpdateRequestDTO;

import java.util.List;

public interface ItemService {
    String addItem(ItemSaveRequestDTO itemSaveRequestDTO);

    List<ItemDTO> getAllItem();

    List<ItemDTO> getAllItemByState(boolean status);

    String updateItem(ItemUpdateRequestDTO itemUpdateRequestDTO, int itemId);

    String deleteItem(int itemId);

    ItemDTO getItemById(int itemId);

    int countAllItems();

    PaginatedResponseItemDTO getAllItemsPaginated(int page, int size);

    PaginatedResponseItemDTO getAllActiveItemsPaginated(int page, int size, boolean activeState);
}
