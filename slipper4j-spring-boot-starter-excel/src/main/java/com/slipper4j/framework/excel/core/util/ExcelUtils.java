package com.slipper4j.framework.excel.core.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.slipper4j.framework.excel.core.ExcelWriteLifecycle;
import com.slipper4j.framework.excel.core.listener.ExcelBatchListener;
import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Excel 工具类
 *
 * @author slipper4j
 */
public class ExcelUtils {

    @Setter
    private static ExcelWriteLifecycle excelWriteLifecycle;

    private static List<ReadListener<Object>> readListeners;

    static public void init(ExcelWriteLifecycle excelWriteLifecycle, List<ReadListener<Object>> readListeners) {
        ExcelUtils.excelWriteLifecycle = excelWriteLifecycle;
        ExcelUtils.readListeners = readListeners;
    }

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param outputStream 响应
     * @param sheetName    Excel sheet 名
     * @param aClass       Excel aClass 头
     * @param data         数据列表哦
     * @param <T>          泛型，保证 aClass 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write(OutputStream outputStream, String sheetName,
                                 List<T> data, Class<T> aClass, boolean autoCloseStream) {
        if (excelWriteLifecycle != null) {
            excelWriteLifecycle.before(data, aClass);
        }
        // 输出 Excel
        ExcelWriterSheetBuilder sheet = EasyExcel.write(outputStream, aClass)
                .autoCloseStream(autoCloseStream) // 不要自动关闭，交给 Servlet 自己处理
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 基于 column 长度，自动适配。最大 255 宽度
                .registerConverter(new LongStringConverter()) // 避免 Long 类型丢失精度
                .sheet(sheetName);
        try {
            sheet.doWrite(data);
        } finally {
            if (excelWriteLifecycle != null) {
                excelWriteLifecycle.after(data, aClass);
            }
        }
    }

    /**
     * 将列表以 Excel 响应给前端
     *
     * @param filename  文件名
     * @param sheetName Excel sheet名
     * @param aClass    Excel head头
     * @param data      数据列表哦
     * @param <T>       泛型，保证 head 和 data 类型的一致性
     * @throws IOException 写入失败的情况
     */
    public static <T> void write2Http(String filename, String sheetName, List<T> data, Class<T> aClass) throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(requestAttributes)) {
            throw new RuntimeException("当前非web环境");
        }
        HttpServletResponse response = requestAttributes.getResponse();
        assert response != null;
        if (!StrUtil.contains(filename, ".")) {
            filename = filename + ".xlsx";
        }
        // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, StandardCharsets.UTF_8.name()));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        // autoCloseStream 不要自动关闭，交给 Servlet 自己处理
        write(response.getOutputStream(), sheetName, data, aClass, false);
    }

    public static <T> List<T> read(MultipartFile file, Class<T> clazz) throws IOException {
        return EasyExcel.read(file.getInputStream(), clazz, null)
                .autoCloseStream(false)  // 不要自动关闭，交给 Servlet 自己处理
                .doReadAllSync();
    }

    /**
     * 读取excel
     *
     * @param file         excel 文件
     * @param clazz        返回 数据clazz
     * @param readListener 监听器
     * @param <T>          返回 数据clazz
     * @return 解析结构
     * @throws IOException 读取失败的情况
     */
    public static <T> void read(MultipartFile file, Class<T> clazz, ReadListener<T> readListener) throws IOException {
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(file.getInputStream(), clazz, readListener)
                .autoCloseStream(false);
        if (readListeners != null && !readListeners.isEmpty()) {
            for (ReadListener<?> listener : readListeners) {
                excelReaderBuilder.registerReadListener(listener);
            }
        }
        excelReaderBuilder.doReadAll();
    }

    /**
     * 批量读取excel
     *
     * @param file      excel 文件
     * @param clazz     返回 数据clazz
     * @param batchSize 批量次数
     * @param consumer  批量处理
     * @return 解析结构
     * @throws IOException 读取失败的情况
     */
    public static <T> void readBatch(MultipartFile file, Class<T> clazz, int batchSize, Consumer<List<T>> consumer) throws IOException {
        ExcelBatchListener<T> readListener = new ExcelBatchListener<>(batchSize, consumer);
        read(file, clazz, readListener);
    }

    /**
     * 批量读取excel
     *
     * @param file     excel 文件
     * @param clazz    返回 数据clazz
     * @param consumer 批量处理
     * @return 解析结构
     * @throws IOException 读取失败的情况
     */
    public static <T> void readBatch(MultipartFile file, Class<T> clazz, Consumer<List<T>> consumer) throws IOException {
        ExcelBatchListener<T> readListener = new ExcelBatchListener<>(consumer);
        read(file, clazz, readListener);
    }
}
