package com.goodstudy.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.goodstudy.media.mapper.MediaFilesMapper;
import com.goodstudy.media.mapper.MediaProcessHistoryMapper;
import com.goodstudy.media.mapper.MediaProcessMapper;
import com.goodstudy.media.model.po.MediaFiles;
import com.goodstudy.media.model.po.MediaProcess;
import com.goodstudy.media.model.po.MediaProcessHistory;
import com.goodstudy.media.service.MediaFileProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Description:
 *
 * @Author: Jack
 * Date: 2023/04/09 16:19
 * Version: 1.0
 */
@Service
@Slf4j
public class MediaFileProcessServiceImpl implements MediaFileProcessService {

    @Autowired
    MediaFilesMapper mediaFilesMapper;

    @Autowired
    MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        List<MediaProcess> mediaProcesses = mediaProcessMapper.selectListByShardIndex(shardTotal, shardIndex, count);
        return mediaProcesses;
    }

    @Override
    public boolean startTask(long id) {
        int result = mediaProcessMapper.startTask(id);
        return result <= 0 ? false : true;
    }

    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
        // 查询任务
        MediaProcess mediaProcess = mediaProcessMapper.selectById(taskId);
        if (mediaProcess == null) {
            log.error("任务不存在，taskId:{}", taskId);
            return;
        }

        // 处理失败，更新任务处理结果
        if (status.equals("3")) {
            LambdaQueryWrapper<MediaProcess> queryWrapperById = new LambdaQueryWrapper<MediaProcess>().eq(MediaProcess::getId, taskId);
            MediaProcess mediaProcess_u = new MediaProcess();
            mediaProcess_u.setStatus("3");
            mediaProcess_u.setErrormsg(errorMsg);
            mediaProcess_u.setFailCount(mediaProcess.getFailCount() + 1);
            mediaProcessMapper.update(mediaProcess_u, queryWrapperById);
            log.debug("更新任务处理状态为失败，任务信息:{}",mediaProcess_u);
        }

        // 任务处理成功
        MediaFiles mediaFiles = mediaFilesMapper.selectById(fileId);
        if (mediaFiles != null) {
            // 更新媒资文件访问地址
            mediaFiles.setUrl(url);
            mediaFilesMapper.updateById(mediaFiles);
        }

        // 更新url和状态
        mediaProcess.setUrl(url);
        mediaProcess.setStatus("2");
        mediaProcess.setFinishDate(LocalDateTime.now());
        mediaProcessMapper.updateById(mediaProcess);

        // 添加到历史记录
        MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
        BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
        mediaProcessHistoryMapper.insert(mediaProcessHistory);
        // 删除任务
        mediaProcessMapper.deleteById(mediaProcess.getId());
    }
}
