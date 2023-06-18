package com.example.smarthouse_tp3

enum class Type(val stringValue: String) {
    OVEN("oven"),
    AC("ac"),
    FAUCET("faucet"),
    VACUUM("vacuum"),
    LIGHT("lamp");

    companion object {
        fun fromString(value: String): Type? {
            return values().find { it.stringValue == value }
        }
    }
}