package ru.mobileup.education_space.solar_system

import me.aartikov.sesame.localizedstring.LocalizedString
import ru.mobileup.core.R
import ru.mobileup.features.solar_system.domain.PlanetId
import ru.mobileup.features.solar_system.ui.PlanetShortInfoViewData

object FakeSolarSystem {

    val detailedSun = PlanetShortInfoViewData(
        id = PlanetId("1"),
        name = LocalizedString.raw("Sun"),
        description = LocalizedString.raw(
            "The Sun is a yellow dwarf star, a hot ball of glowing gases at the heart of our solar system. Its gravity holds everything from the biggest planets to tiny debris in its orbit. \\n Our Sun is a 4.5 billion-year-old star – a hot glowing ball of hydrogen and helium at the center of our solar system. The Sun is about 93 million miles (150 million kilometers) from Earth, and without its energy, life as we know it could not exist here on our home planet. \\n The Sun is the largest object in our solar system. The Sun’s volume would need 1.3 million Earths to fill it. Its gravity holds the solar system together, keeping everything from the biggest planets to the smallest bits of debris in orbit around it. The hottest part of the Sun is its core, where temperatures top 27 million degrees Fahrenheit (15 million degrees Celsius). The Sun’s activity, from its powerful eruptions to the steady stream of charged particles it sends out, influences the nature of space throughout the solar system. \\n NASA and other international space agencies monitor the Sun 24/7 with a fleet of spacecraft, studying everything from its atmosphere to its surface, and even peering inside the Sun using special instruments. Sun-exploring spacecraft include Parker Solar Probe, Solar Orbiter, SOHO, ACE, IRIS, WIND, Hinode, Solar Dynamics Observatory, and STEREO."
        ),
        imageRes = R.drawable.img_sun
    )
}