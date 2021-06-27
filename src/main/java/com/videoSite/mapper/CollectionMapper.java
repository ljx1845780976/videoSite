package com.videoSite.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.videoSite.entity.Comment;
import com.videoSite.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-03-22
 */
@Mapper
public interface CollectionMapper  {
    void addCollectionByUsername(@Param("username") String username,@Param("videoId") Integer videoId);
    void cancelCollectionByUsername(@Param("username") String username,@Param("videoId") Integer videoId);
    IPage<Video> getCollectionByUsername(Page<Video> page, @Param("username") String username);
    Integer isExisted(@Param("username") String username,@Param("videoId") Integer videoId);
}
