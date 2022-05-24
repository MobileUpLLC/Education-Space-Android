package ru.mobileup.education_space.utils

object FakeData {
    val launchersEmptyResponse = "[]"

    val launchersListResponse = """
        [
          {
            "fairings": null,
            "links": {
              "patch": {
                "small": "https://i.imgur.com/h4x6VFd.png",
                "large": "https://i.imgur.com/RR8JLzm.png"
              },
              "reddit": {
                "campaign": null,
                "launch": null,
                "media": null,
                "recovery": null
              },
              "flickr": {
                "small": [],
                "original": []
              },
              "presskit": null,
              "webcast": null,
              "youtube_id": null,
              "article": null,
              "wikipedia": null
            },
            "static_fire_date_utc": null,
            "static_fire_date_unix": null,
            "net": false,
            "window": null,
            "rocket": "5e9d0d95eda69973a809d1ec",
            "success": null,
            "failures": [],
            "details": "Axiom Mission 1 (or Ax-1) is a planned SpaceX Crew Dragon mission to the International Space Station (ISS), operated by SpaceX on behalf of Axiom Space. The flight will launch no earlier than 31 March 2022 and send four people to the ISS for an eight-day stay",
            "crew": [
              "61eefc9c9eb1064137a1bd77",
              "61eefcf89eb1064137a1bd79",
              "61eefd5b9eb1064137a1bd7a",
              "61eefdbf9eb1064137a1bd7b"
            ],
            "ships": [],
            "capsules": [],
            "payloads": [
              "61eefb129eb1064137a1bd74"
            ],
            "launchpad": "5e9e4502f509094188566f88",
            "flight_number": 155,
            "name": "Ax-1",
            "date_utc": "2022-03-30T18:45:00.000Z",
            "date_unix": 1648665900,
            "date_local": "2022-03-30T14:45:00-04:00",
            "date_precision": "hour",
            "upcoming": true,
            "cores": [
              {
                "core": null,
                "flight": null,
                "gridfins": null,
                "legs": null,
                "reused": null,
                "landing_attempt": null,
                "landing_success": null,
                "landing_type": null,
                "landpad": null
              }
            ],
            "auto_update": true,
            "tbd": false,
            "launch_library_id": "a3eeb03b-a209-4255-91b5-772dc0d2150e",
            "id": "61eefaa89eb1064137a1bd73"
          },
          {
            "fairings": {
              "reused": null,
              "recovery_attempt": null,
              "recovered": null,
              "ships": []
            },
            "links": {
              "patch": {
                "small": null,
                "large": null
              },
              "reddit": {
                "campaign": null,
                "launch": null,
                "media": null,
                "recovery": "https://www.reddit.com/r/spacex/comments/k2ts1q/rspacex_fleet_updates_discussion_thread/"
              },
              "flickr": {
                "small": [],
                "original": []
              },
              "presskit": null,
              "webcast": null,
              "youtube_id": null,
              "article": null,
              "wikipedia": null
            },
            "static_fire_date_utc": null,
            "static_fire_date_unix": null,
            "net": false,
            "window": null,
            "rocket": "5e9d0d95eda69973a809d1ec",
            "success": null,
            "failures": [],
            "details": null,
            "crew": [],
            "ships": [],
            "capsules": [],
            "payloads": [
              "5fe3b2abb3467846b3242172"
            ],
            "launchpad": "5e9e4502f509092b78566f87",
            "flight_number": 158,
            "name": "SARah 1",
            "date_utc": "2022-04-01T00:00:00.000Z",
            "date_unix": 1648771200,
            "date_local": "2022-03-31T17:00:00-07:00",
            "date_precision": "month",
            "upcoming": true,
            "cores": [
              {
                "core": null,
                "flight": null,
                "gridfins": null,
                "legs": null,
                "reused": null,
                "landing_attempt": null,
                "landing_success": null,
                "landing_type": null,
                "landpad": null
              }
            ],
            "auto_update": true,
            "tbd": false,
            "launch_library_id": null,
            "id": "5fe3af43b3467846b324215e"
          }
        ]
    """.trimIndent()
}