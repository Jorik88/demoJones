package com.example.alex.demoJones.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmlCheckResult {

    private List<String> fullNameData = new ArrayList<>();
    private List<String> addressData = new ArrayList<>();
}
