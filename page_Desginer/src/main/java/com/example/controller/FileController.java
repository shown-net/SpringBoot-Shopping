package com.example.controller;

import com.example.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@CrossOrigin
public class FileController {
    private final String baseUrl = System.getProperty("user.dir") + "/images/";

    // 获取商品图片
    @GetMapping("/images/download/{imageSrc}")
    public byte[] getImage(@PathVariable String imageSrc) throws IOException {
        return Files.readAllBytes(Path.of(baseUrl + imageSrc));
    }

    // 获取走马灯商品图片的URL地址(文件名）
    @GetMapping("/images/download/carousel")
    public Result getCarouselImageUrl() throws IOException {
        List<String> UrlList = new ArrayList<>();
        File directory = new File(baseUrl + "carousel");
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    UrlList.add(file.getName());
                }
            }
        }
        return Result.success("获取走马灯图片成功", UrlList);
    }

    // 获取走马灯商品图片
    @GetMapping("/images/download/carousel/{imageSrc}")
    public byte[] getCarouseImage(@PathVariable String imageSrc) throws IOException {
        return Files.readAllBytes(Path.of(baseUrl + "carousel/" + imageSrc));
    }

    @PostMapping("/images/upload")
    public Result uploadImage(MultipartFile file) throws IOException {
        String orginalFilename = file.getOriginalFilename();//文件名称
        final Path path = Path.of(baseUrl);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        if (Files.exists(Path.of(baseUrl + orginalFilename))) {   //如果文件名已存在则随机创建新的文件名
            orginalFilename = System.currentTimeMillis() + "_" + orginalFilename;
        }
        File saveFile = new File(baseUrl + orginalFilename);
        file.transferTo(saveFile);//存储文件到本地磁盘
        return Result.success(orginalFilename);
    }

    @PostMapping("/images/delete")
    public Result deleteImage(@RequestBody String FilePath) throws IOException {
        final Path path = Path.of(baseUrl + FilePath.replace("\"", ""));
        if (Files.exists(path)) {
            Files.delete(path);
            return Result.success("ok");
        }
        return Result.error("not Exist", null);
    }


}
