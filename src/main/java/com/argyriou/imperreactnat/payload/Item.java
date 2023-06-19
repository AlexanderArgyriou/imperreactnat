package com.argyriou.imperreactnat.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
    private String info = "";

    public Item(Item item) {
        info= item.getInfo();
    }
}

