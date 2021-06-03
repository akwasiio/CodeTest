package com.mpharma.codetest

interface BaseMapper<Input, Output> {
    fun map(input: Input): Output

    fun mapInputList(input: List<Input>): List<Output>
}