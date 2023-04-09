package com.goodstudy.media.service;

import com.goodstudy.media.model.po.MediaProcess;

import java.util.List;

/**
 * Description: 媒资文件处理业务方法
 *
 * @Author: Jack
 * Date: 2023/04/09 16:18
 * Version: 1.0
 */
public interface MediaFileProcessService {

    /**
     * 根据分片索引查询待处理文件列表
     *
     * @param shardIndex int
     * @param shardTotal int
     * @param count      int
     * @return java.util.List<com.goodstudy.media.model.po.MediaProcess>
     * @author Jack
     * @date 2023/4/9 16:18
     * @update_by Jack
     * @update_at 2023/4/9 16:18
     * @creed Talk is cheap, show me the comment !!!
     */
    List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);


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
    boolean startTask(long id);


    /**
     * 保存处理完成状态
     *
     * @param taskId   java.lang.Long 任务id
     * @param status   java.lang.String 任务状态
     * @param fileId   java.lang.String 文件id
     * @param url      java.lang.String 文件url
     * @param errorMsg java.lang.String 错误信息
     * @return void
     * @author Jack
     * @date 2023/4/9 17:10
     * @update_by Jack
     * @update_at 2023/4/9 17:10
     * @creed Talk is cheap, show me the comment !!!
     */
    void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg);
}
