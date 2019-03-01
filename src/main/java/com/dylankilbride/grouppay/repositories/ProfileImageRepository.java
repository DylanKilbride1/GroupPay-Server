package com.dylankilbride.grouppay.repositories;

import com.dylankilbride.grouppay.models.ProfileImage;
import org.springframework.data.repository.CrudRepository;

public interface ProfileImageRepository extends CrudRepository<ProfileImage, Integer> {

	ProfileImage findProfileImageByImageId(long imageId);
}
