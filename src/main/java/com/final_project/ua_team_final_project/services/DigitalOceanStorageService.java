package com.final_project.ua_team_final_project.services;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DigitalOceanStorageService {

    private final S3Client s3Client;

    private final String BUCKET_NAME = Dotenv.load().get("DO_BUCKET_NAME");

    private final String HISTORY_FOLDER = Dotenv.load().get("DO_BUCKET_SUPPLY_ORDERS_HISTORY");

    public List<String> listCsvFiles() {
        ListObjectsV2Request request = ListObjectsV2Request.builder()
                .bucket(BUCKET_NAME)
                .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        return response.contents().stream()
                .map(S3Object::key)
                .filter(key -> key.endsWith(".csv"))
                .filter(key -> !key.contains(HISTORY_FOLDER))
                .collect(Collectors.toList());
    }

    public InputStream downloadFile(String fileName) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(fileName)
                .build();
        return s3Client.getObject(request);
    }

    public List<String> uploadFiles(Map<String, OutputStream> files) {

        List<String> fileNames = new ArrayList<>();

        files.forEach((fileName, outputStream) -> {
            String filePath = HISTORY_FOLDER + fileName;
            try {
                byte[] fileData = toByteArray(outputStream);

                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(filePath)
                        .contentType("text/csv")
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

                fileNames.add(fileName);

                System.out.println("File " + fileName + " successfully loaded to S3.");

            } catch (IOException e) {
                System.err.println("Error processing " + fileName);
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Error by uploading to S3 " + fileName);
                e.printStackTrace();
            }
        });
        return fileNames;
    }

    private byte[] toByteArray(OutputStream outputStream) throws IOException {
        if (outputStream instanceof ByteArrayOutputStream) {
            return ((ByteArrayOutputStream) outputStream).toByteArray();
        } else {
            throw new IOException("OutputStream should be ByteArrayOutputStream");
        }
    }
}

