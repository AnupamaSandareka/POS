package com.pos.kuppiya.pointofsale.controller;

import com.pos.kuppiya.pointofsale.dto.ItemDTO;
import com.pos.kuppiya.pointofsale.dto.paginated.PaginatedResponseItemDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemSaveRequestDTO;
import com.pos.kuppiya.pointofsale.dto.request.ItemUpdateRequestDTO;
import com.pos.kuppiya.pointofsale.service.ItemService;
import com.pos.kuppiya.pointofsale.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(path = "/save")
    public ResponseEntity<StandardResponse> addItem(@RequestBody ItemSaveRequestDTO itemSaveRequestDTO){
        String result = itemService.addItem(itemSaveRequestDTO);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,result+" item successfully saved",result),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            path = {"get-all-item"}
    )
    public ResponseEntity<StandardResponse> getAllItem(){
        List<ItemDTO> itemDTOList = itemService.getAllItem();
         return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"success",itemDTOList),
                HttpStatus.OK
        );
    }

    @GetMapping(
            path = {"get-all-item-by-state"},
            params = {"state"}
    )
    public ResponseEntity<StandardResponse> getAllItemByState(@RequestParam(value = "state") String state){

        if(state.equalsIgnoreCase("active") | state.equalsIgnoreCase("inactive")){

            boolean status = state.equalsIgnoreCase("active")?true:false;

            List<ItemDTO> itemDTOList = itemService.getAllItemByState(status);
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "success", itemDTOList),
                    HttpStatus.OK
            );
        }
        else {
            List<ItemDTO> itemDTOList = itemService.getAllItem();
            return new ResponseEntity<StandardResponse>(
                    new StandardResponse(200, "success", itemDTOList),
                    HttpStatus.OK
            );
        }
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<StandardResponse> updateItem(@RequestBody ItemUpdateRequestDTO itemUpdateRequestDTO,
                                                       @PathVariable(value = "id") int itemId){
        String result = itemService.updateItem(itemUpdateRequestDTO,itemId);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,result+" item successfully updated",result),
                HttpStatus.CREATED
        );
    }

    @PutMapping(path = "/delete-by-id/{id}")
    public ResponseEntity<StandardResponse> deleteItemById(@PathVariable(value = "id") int itemId){
        String result = itemService.deleteItem(itemId);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(201,result+" item successfully deleted",result),
                HttpStatus.CREATED
        );
    }

    @GetMapping(
            path = {"get-item-by-id"},
            params = {"id"}
    )
    public ResponseEntity<StandardResponse> getItemById(@RequestParam(value = "id") int itemId){
        ItemDTO itemDTO = itemService.getItemById(itemId);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"success",itemDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(
            path = {"count-all-items"}
    )
    public ResponseEntity<StandardResponse> countAllItems(){
        int itemCount = itemService.countAllItems();
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"success",itemCount),
                HttpStatus.OK
        );
    }

    @GetMapping(
            path = {"get-all-items-paginated"},
            params = {"page","size"}
    )
    public ResponseEntity<StandardResponse>getAllItemsPaginated(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") @Max(50) int size
    ){
        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getAllItemsPaginated(page,size);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"success",paginatedResponseItemDTO),
                HttpStatus.OK
        );
    }

    @GetMapping(
            path = {"get-all-active-items-paginated"},
            params = {"page","size","activeState"}
    )
    public ResponseEntity<StandardResponse>getAllActiveItemsPaginated(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "activeState") boolean activeState
    ){
        PaginatedResponseItemDTO paginatedResponseItemDTO = itemService.getAllActiveItemsPaginated(page,size,activeState);
        return new ResponseEntity<StandardResponse>(
                new StandardResponse(200,"success",paginatedResponseItemDTO),
                HttpStatus.OK
        );
    }
}
