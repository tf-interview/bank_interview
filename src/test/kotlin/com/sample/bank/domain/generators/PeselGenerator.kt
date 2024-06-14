package com.sample.bank.domain.generators

object PeselGenerator {
    fun validPesel() = "71072589621"
    fun invalidPesel() = "11111111A"

    val validPesels = listOf(
        "57050938931",
        "82050238453",
        "76121415258",
        "50073046151",
        "78041384988",
        "94121964213",
        "58122695657",
        "51112535744",
        "58121542226",
        "56013081789",
    )
}