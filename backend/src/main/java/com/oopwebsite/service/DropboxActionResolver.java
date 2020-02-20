package com.oopwebsite.service;

    @FunctionalInterface
    interface DropboxActionResolver<T> {

        T perform() throws Exception;

    }
