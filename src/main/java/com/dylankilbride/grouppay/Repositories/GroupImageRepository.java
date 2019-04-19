package com.dylankilbride.grouppay.Repositories;

import com.dylankilbride.grouppay.Models.GroupImage;
import org.springframework.data.repository.CrudRepository;

public interface GroupImageRepository extends CrudRepository<GroupImage, Integer>{

	GroupImage findGroupImageByGroupImageId(long imageId);

}
