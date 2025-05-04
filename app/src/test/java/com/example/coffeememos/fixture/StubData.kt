package com.example.coffeememos.fixture

sealed class StubData {
    sealed class Country : StubData() {
        data object Brazil : Country()
        data object Columbia : Country()
        data object Ethiopia : Country()
    }

    sealed class Tool : StubData() {
        data object Hario : Tool()
        data object Merita : Tool()
        data object Karita : Tool()
    }
}
