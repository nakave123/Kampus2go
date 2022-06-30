import 'dotenv/config';

const {
    S3Client,
    PutObjectCommand,
    GetObjectCommand,
    DeleteObjectCommand
} = require("@aws-sdk/client-s3");

//required for local machine
const {
    fromIni
} = require("@aws-sdk/credential-provider-ini");
const region = 'us-east-1';
const s3 = new S3Client({
    region,
    credentials: fromIni({
        profile: 'info6150'
    })
});

// const fs = require('fs');
//import fs from "fs";

 
const bucketName = process.env.S3_BUCKETNAME;

// uploads a file to s3
export function uploadFile(file,key) {
 //   const fileStream = fs.createReadStream(file.path)
    const uploadParams = {
        Bucket: bucketName,
        Body: file,
        Key: key
    }
    return s3.send(new PutObjectCommand(uploadParams))
}

export function getFile(key) {
    console.log("in get file");
    const getParams = {
        Key: key,
        Bucket: bucketName
    }
    return s3.send(new GetObjectCommand(getParams))
}


export function deleteFile(key) {

    const deleteParams = {
        Key: key,
        Bucket: bucketName
    }
    return s3.send(new DeleteObjectCommand(deleteParams))
}

module.exports= {
    uploadFile,
    getFile,
    deleteFile
}