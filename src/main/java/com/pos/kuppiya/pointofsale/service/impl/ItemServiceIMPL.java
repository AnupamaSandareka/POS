package com.pos.kuppiya.pointofsale.service.impl;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.paginated.PaginatedResponseItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.entity.Item;
import com.pos.kuppiya.pointofsale.exception.EntryDuplicateException;
import com.pos.kuppiya.pointofsale.exception.NotFoundException;
import com.pos.kuppiya.pointofsale.repository.ItemRepo;
import com.pos.kuppiya.pointofsale.service.ItemService;
import com.pos.kuppiya.pointofsale.util.mappers.ItemMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemServiceIMPL implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String addItem(ItemSaveRequestDTO itemSaveRequestDTO) {
        Item item = itemMapper.RequestDtoToEntity(itemSaveRequestDTO);
//        Item item = modelMapper.map(itemSaveRequestDTO,Item.class); if use model mapper
        item.setActiveState(true);
        if (!itemRepo.existsById(item.getItemId())) {
            return itemRepo.save(item).getItemName();
        } else {
            throw new EntryDuplicateException("Already in database");
        }
    }

    @Override
    public List<ItemDTO> getAllItem() {
        List<Item> itemList = itemRepo.findAll();
        List<ItemDTO> itemDTOList = modelMapper.map(itemList,new TypeToken<List<ItemDTO>>(){}.getType());
        return  itemDTOList;
    }

    @Override
    public List<ItemDTO> getAllItemByState(boolean status) {
        List<Item> itemList = itemRepo.findAllByActiveStateEquals(status);
        List<ItemDTO> itemDTOList = modelMapper.map(itemList,new TypeToken<List<ItemDTO>>(){}.getType());
        return  itemDTOList;
    }

    @Override
    public String updateItem(ItemUpdateRequestDTO itemUpdateRequestDTO, int itemId) {
        if (itemRepo.existsById(itemId)){
            Item item = itemRepo.getById(itemId);
            item.setBalanceQty(itemUpdateRequestDTO.getBalanceQty());
            item.setSupplierPrice(itemUpdateRequestDTO.getSupplierPrice());
            item.setSellingPrice(itemUpdateRequestDTO.getSellingPrice());
            itemRepo.save(item);
            return item.getItemName();
        }
        else{
            throw new NotFoundException("not found this id");
        }
    }

    @Override
    public String deleteItem(int itemId) {
        if(itemRepo.existsById(itemId)){
            Item item = itemRepo.getById(itemId);
            itemRepo.deleteById(itemId);
            return item.getItemName();
        }
        else{
            throw new NotFoundException("not found this id");
        }
    }

    @Override
    public ItemDTO getItemById(int itemId) {
        if(itemRepo.existsById(itemId)){
            Item item = itemRepo.getById(itemId);
            ItemDTO itemDTO = modelMapper.map(item,ItemDTO.class);
            return itemDTO;
        }
        else{
            throw new NotFoundException("not found this id");
        }
    }

    @Override
    public int countAllItems() {
        int itemCount = itemRepo.countAllByActiveStateEquals(true);
        return itemCount;
    }

    @Override
    public PaginatedResponseItemDTO getAllItemsPaginated(int page, int size) {
        Page<Item> getAllByPaginated = itemRepo.findAll(PageRequest.of(page,size));

        return new PaginatedResponseItemDTO(
                itemMapper.pageToList(getAllByPaginated),
                itemRepo.count()
        );
    }

    @Override
    public PaginatedResponseItemDTO getAllActiveItemsPaginated(int page, int size, boolean activeState) {
        Page<Item> getAllByPaginated = itemRepo.findAllByActiveStateEquals(activeState,PageRequest.of(page,size));

        return new PaginatedResponseItemDTO(
                itemMapper.pageToList(getAllByPaginated),
                itemRepo.countAllByActiveStateEquals(activeState)
        );
    }
}