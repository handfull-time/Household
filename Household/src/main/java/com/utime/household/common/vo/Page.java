package com.utime.household.common.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Page<T> {

    public Page(List<T> data) {
        this.data = data;
    }

    private List<T> data;
    private int recordsFiltered;
    private int recordsTotal;
    private int draw;

    @Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}
}