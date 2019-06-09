package dev.rezastallone.cartrackchallange

import dev.rezastallone.cartrackchallange.data.Address
import dev.rezastallone.cartrackchallange.data.Company
import dev.rezastallone.cartrackchallange.data.Contact
import dev.rezastallone.cartrackchallange.data.Geo

val contactForTest = Contact(
    1,
    "roger",
    "roger777",
    "roger@gmail.com",
    Address(
        "Aljuned Street",
        "NO. 1",
        "Singapore",
        "1",
        Geo(
            "1", "2"
        )
    ),
    "192873",
    "www.google.com",
    Company(
        "Google",
        "Dont be evil",
        "idk"
    )
)