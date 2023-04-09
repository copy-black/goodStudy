package com.goodstudy.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodstudy.media.model.po.MediaProcess;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface MediaProcessMapper extends BaseMapper<MediaProcess> {

    /**
     * 根据分片索引查询待处理文件列表
     *
     * @param shardTotal int
     * @param shardIndex int
     * @param count      int
     * @return java.util.List<com.goodstudy.media.model.po.MediaProcess>
     * @author Jack
     * @date 2023/4/9 16:00
     * @update_by Jack
     * @update_at 2023/4/9 16:00
     * @creed Talk is cheap, show me the comment !!!
     */
    @Select("select * from media_process t where t.id % #{shardTotal} = #{shardIndex} and (t.status = '1' or t.status = '3') and t.fail_count < 3 limit #{count}")
    List<MediaProcess> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardIndex") int shardIndex, @Param("count") int count);

    /**
     * 根据id更新任务状态
     *
     * @param id long
     * @return int
     * @author Jack
     * @date 2023/4/9 17:07
     * @update_by Jack
     * @update_at 2023/4/9 17:07
     * @creed Talk is cheap, show me the comment !!!
     */
    @Update("update media_process m  set m.status = '4' where (m.status = '1' or m.status = '3') and m.fail_count < 3 and m.id = #{id}")
    int startTask(@Param("id") long id);
}
