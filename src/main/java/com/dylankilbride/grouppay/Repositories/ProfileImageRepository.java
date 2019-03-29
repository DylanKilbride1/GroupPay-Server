package com.dylankilbride.grouppay.Repositories;

import com.dylankilbride.grouppay.Models.ProfileImage;
import org.springframework.data.repository.CrudRepository;

public interface ProfileImageRepository extends CrudRepository<ProfileImage, Integer> {

	ProfileImage findProfileImageByImageId(long imageId);
}
