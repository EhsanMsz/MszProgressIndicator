# MSZ Progress Indicator
[![maven-central-badge]][maven-central]
[![license-badge]][license]
[![issues-badge]][issues]
![compose-badge]
[![code-factor-badge]][code-factor]
[![publish-badge]][publish]


### A collection of progress indicators for Android Jetpack Compose, inspired by [loaders.css][1].

[document][document]

## Demo

<div align="center">
    <img src="screenshot/progress_indicator.gif">
</div>

## Download

Gradle:
```kotlin
dependencies {
    implementation("com.ehsanmsz:msz-progress-indicator:0.8.0")
}
```

Maven:
```xml
<dependency>
  <groupId>com.ehsanmsz</groupId>
  <artifactId>msz-progress-indicator</artifactId>
  <version>0.8.0</version>
  <type>aar</type>
</dependency>
```


## Progress Indicators
| Progress Indicators |  |  |  |
|--|--|--|--|
| BallPulse | BallGridPulse | BallClipRotate | BallClipRotatePulse |
| SquareSpin | BallClipRotateMultiple | BallPulseRise | BallRotate |
| CubeTransition | BallZigZag | BallZigZagDeflect | BallTrianglePath  |
| BallScale | LineScale | LineScaleParty | BallScaleMultiple |
| BallPulseSync | BallBeat | LineScalePulseOut | LineScalePulseOutRapid |
| BallScaleRipple | BallScaleRippleMultiple | BallSpinFadeLoader | LineSpinFadeLoader |
| TriangleSkewSpin | Pacman | BallGridBeat | SemiCircleSpin |

---

## Customization
Progress indicators can be used with default parameters value:

```kotlin
@Composable
fun ProgressIndicator() {
    LineSpinFadeLoader()
}
```

parameters such as `animationDuration` and `color` are common, but each `progress indicator` has its own parameters.

for example:

```kotlin
BallScaleRippleMultipleProgressIndicator(
    modifier = Modifier,
    color = Color.White,
    animationDuration = 800,
    animationDelay = 200,
    startDelay = 0,
    diameter = 40.dp,
    strokeWidth = 1.5.dp,
    ballCount = 4
)
```

each `progress indicator` can be easily customized by changing parameters value.

|   BallPulse   |   LineScale   |    BallSpinFadeLoader    |
|---------------|---------------|--------------------------|
| ![ball-pulse] | ![line-scale] | ![ball-spin-fade-loader] |

---

## License

```
Copyright 2021 Ehsan Msz

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[maven-central-badge]: https://img.shields.io/maven-central/v/com.ehsanmsz/msz-progress-indicator?label=Maven%20Central&color=blue

[license-badge]: https://img.shields.io/github/license/ehsanmsz/mszprogressindicator?color=blue

[issues-badge]: https://img.shields.io/github/issues/ehsanmsz/mszprogressindicator?color=blue

[compose-badge]: https://img.shields.io/badge/ComposeBom-2024.03.00-blue

[code-factor-badge]: https://img.shields.io/codefactor/grade/github/ehsanmsz/mszprogressindicator

[publish-badge]: https://img.shields.io/github/actions/workflow/status/ehsanmsz/mszprogressindicator/publish.yaml

[maven-central]: https://search.maven.org/artifact/com.ehsanmsz/msz-progress-indicator/0.2.0/aar

[license]: https://github.com/EhsanMsz/MszProgressIndicator/blob/master/LICENSE

[issues]: https://github.com/EhsanMsz/MszProgressIndicator/issues

[code-factor]: https://www.codefactor.io/repository/github/ehsanmsz/mszprogressindicator/overview/master

[publish]: https://github.com/EhsanMsz/MszProgressIndicator/actions/workflows/publish.yaml

[document]: https://ehsanmsz.com/open-source/msz-progress-indicator


[1]: https://github.com/ConnorAtherton/loaders.css

[ball-pulse]: screenshot/ball_pulse_custom.gif

[line-scale]: screenshot/line_scale_custom.gif

[ball-spin-fade-loader]: screenshot/ball_spin_fade_loader_custom.gif
