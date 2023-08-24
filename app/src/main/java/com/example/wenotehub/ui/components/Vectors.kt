package com.example.wenotehub.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundImage(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "vector",
            defaultWidth = 327.dp,
            defaultHeight = 349.dp,
            viewportWidth = 327f,
            viewportHeight = 349f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFB3A0EB)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 40f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(180f, 268.5f)
                curveTo(180f, 341.255f, 110.747f, 405f, 19f, 405f)
                curveTo(-72.7473f, 405f, -142f, 341.255f, -142f, 268.5f)
                curveTo(-142f, 195.745f, -72.7473f, 132f, 19f, 132f)
                curveTo(110.747f, 132f, 180f, 195.745f, 180f, 268.5f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFB3A0EB)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 40f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(542f, 66.5f)
                curveTo(542f, 139.255f, 472.747f, 203f, 381f, 203f)
                curveTo(289.253f, 203f, 220f, 139.255f, 220f, 66.5f)
                curveTo(220f, -6.2554f, 289.253f, -70f, 381f, -70f)
                curveTo(472.747f, -70f, 542f, -6.2554f, 542f, 66.5f)
                close()
            }
        }.build()
    }
}

@Composable
fun HomeVector(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "vector",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFB3A0EB)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(28f, 8f)
                curveTo(27.6464f, 8f, 27.3073f, 8.1405f, 27.0572f, 8.3905f)
                curveTo(26.8072f, 8.6406f, 26.6667f, 8.9797f, 26.6667f, 9.3333f)
                verticalLineTo(22.6667f)
                curveTo(26.6667f, 23.7275f, 26.2453f, 24.745f, 25.4951f, 25.4951f)
                curveTo(24.745f, 26.2452f, 23.7276f, 26.6667f, 22.6667f, 26.6667f)
                horizontalLineTo(9.33335f)
                curveTo(8.9797f, 26.6667f, 8.6406f, 26.8071f, 8.3905f, 27.0572f)
                curveTo(8.1405f, 27.3072f, 8f, 27.6464f, 8f, 28f)
                curveTo(8f, 28.3536f, 8.1405f, 28.6928f, 8.3905f, 28.9428f)
                curveTo(8.6406f, 29.1929f, 8.9797f, 29.3333f, 9.3333f, 29.3333f)
                horizontalLineTo(22.6667f)
                curveTo(24.4348f, 29.3333f, 26.1305f, 28.631f, 27.3807f, 27.3807f)
                curveTo(28.631f, 26.1305f, 29.3334f, 24.4348f, 29.3334f, 22.6667f)
                verticalLineTo(9.33333f)
                curveTo(29.3334f, 8.9797f, 29.1929f, 8.6406f, 28.9428f, 8.3905f)
                curveTo(28.6928f, 8.1405f, 28.3536f, 8f, 28f, 8f)
                close()
                moveTo(24f, 20f)
                verticalLineTo(6.66667f)
                curveTo(24f, 5.6058f, 23.5786f, 4.5884f, 22.8284f, 3.8382f)
                curveTo(22.0783f, 3.0881f, 21.0609f, 2.6667f, 20f, 2.6667f)
                horizontalLineTo(6.66669f)
                curveTo(5.6058f, 2.6667f, 4.5884f, 3.0881f, 3.8383f, 3.8382f)
                curveTo(3.0881f, 4.5884f, 2.6667f, 5.6058f, 2.6667f, 6.6667f)
                verticalLineTo(20f)
                curveTo(2.6667f, 21.0609f, 3.0881f, 22.0783f, 3.8383f, 22.8284f)
                curveTo(4.5884f, 23.5786f, 5.6058f, 24f, 6.6667f, 24f)
                horizontalLineTo(20f)
                curveTo(21.0609f, 24f, 22.0783f, 23.5786f, 22.8284f, 22.8284f)
                curveTo(23.5786f, 22.0783f, 24f, 21.0609f, 24f, 20f)
                verticalLineTo(20f)
                close()
                moveTo(13.3334f, 5.33333f)
                horizontalLineTo(16f)
                verticalLineTo(11.8133f)
                lineTo(15.52f, 11.4133f)
                curveTo(15.2804f, 11.2138f, 14.9785f, 11.1045f, 14.6667f, 11.1045f)
                curveTo(14.3549f, 11.1045f, 14.0529f, 11.2138f, 13.8134f, 11.4133f)
                lineTo(13.3334f, 11.8133f)
                verticalLineTo(5.33333f)
                close()
                moveTo(5.33335f, 20f)
                verticalLineTo(6.66667f)
                curveTo(5.3334f, 6.313f, 5.4738f, 5.9739f, 5.7239f, 5.7239f)
                curveTo(5.9739f, 5.4738f, 6.3131f, 5.3333f, 6.6667f, 5.3333f)
                horizontalLineTo(10.6667f)
                verticalLineTo(14.6667f)
                curveTo(10.6667f, 14.9216f, 10.7397f, 15.1711f, 10.8772f, 15.3858f)
                curveTo(11.0146f, 15.6004f, 11.2107f, 15.7712f, 11.4422f, 15.8778f)
                curveTo(11.6737f, 15.9844f, 11.9309f, 16.0225f, 12.1834f, 15.9874f)
                curveTo(12.4359f, 15.9524f, 12.673f, 15.8457f, 12.8667f, 15.68f)
                lineTo(14.6667f, 14.1733f)
                lineTo(16.4667f, 15.68f)
                curveTo(16.7082f, 15.8865f, 17.0156f, 16f, 17.3334f, 16f)
                curveTo(17.5263f, 15.9986f, 17.7168f, 15.9578f, 17.8934f, 15.88f)
                curveTo(18.1248f, 15.7729f, 18.3207f, 15.6017f, 18.4577f, 15.3866f)
                curveTo(18.5948f, 15.1715f, 18.6673f, 14.9217f, 18.6667f, 14.6667f)
                verticalLineTo(5.33333f)
                horizontalLineTo(20f)
                curveTo(20.3536f, 5.3333f, 20.6928f, 5.4738f, 20.9428f, 5.7239f)
                curveTo(21.1929f, 5.9739f, 21.3334f, 6.313f, 21.3334f, 6.6667f)
                verticalLineTo(20f)
                curveTo(21.3334f, 20.3536f, 21.1929f, 20.6928f, 20.9428f, 20.9428f)
                curveTo(20.6928f, 21.1929f, 20.3536f, 21.3333f, 20f, 21.3333f)
                horizontalLineTo(6.66669f)
                curveTo(6.3131f, 21.3333f, 5.9739f, 21.1929f, 5.7239f, 20.9428f)
                curveTo(5.4738f, 20.6928f, 5.3334f, 20.3536f, 5.3334f, 20f)
                close()
            }
        }.build()
    }
}

@Composable
fun SettingsVector(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "vector",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFB3A0EB)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(28.0005f, 8.8984f)
                curveTo(28.0005f, 11.6032f, 25.8069f, 13.7968f, 23.1021f, 13.7968f)
                curveTo(20.3973f, 13.7968f, 18.2049f, 11.6032f, 18.2049f, 8.8984f)
                curveTo(18.2049f, 6.1936f, 20.3973f, 4f, 23.1021f, 4f)
                curveTo(25.8069f, 4f, 28.0005f, 6.1936f, 28.0005f, 8.8984f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFB3A0EB)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.7956f, 8.8984f)
                curveTo(13.7956f, 11.6032f, 11.6032f, 13.7968f, 8.8972f, 13.7968f)
                curveTo(6.1936f, 13.7968f, 4f, 11.6032f, 4f, 8.8984f)
                curveTo(4f, 6.1936f, 6.1936f, 4f, 8.8972f, 4f)
                curveTo(11.6032f, 4f, 13.7956f, 6.1936f, 13.7956f, 8.8984f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFB3A0EB)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(28.0005f, 23.0159f)
                curveTo(28.0005f, 25.7207f, 25.8069f, 27.9131f, 23.1021f, 27.9131f)
                curveTo(20.3973f, 27.9131f, 18.2049f, 25.7207f, 18.2049f, 23.0159f)
                curveTo(18.2049f, 20.3111f, 20.3973f, 18.1175f, 23.1021f, 18.1175f)
                curveTo(25.8069f, 18.1175f, 28.0005f, 20.3111f, 28.0005f, 23.0159f)
                close()
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFB3A0EB)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.7956f, 23.0159f)
                curveTo(13.7956f, 25.7207f, 11.6032f, 27.9131f, 8.8972f, 27.9131f)
                curveTo(6.1936f, 27.9131f, 4f, 25.7207f, 4f, 23.0159f)
                curveTo(4f, 20.3111f, 6.1936f, 18.1175f, 8.8972f, 18.1175f)
                curveTo(11.6032f, 18.1175f, 13.7956f, 20.3111f, 13.7956f, 23.0159f)
                close()
            }
        }.build()
    }
}

@Composable
fun PlusVector(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "vector",
            defaultWidth = 64.dp,
            defaultHeight = 64.dp,
            viewportWidth = 64f,
            viewportHeight = 64f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF8962F8)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(63f, 32f)
                arcTo(31f, 31f, 0f, isMoreThanHalf = false, isPositiveArc = true, 32f, 63f)
                arcTo(31f, 31f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 32f)
                arcTo(31f, 31f, 0f, isMoreThanHalf = false, isPositiveArc = true, 63f, 32f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF8962F8)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(41.3333f, 30.6667f)
                horizontalLineTo(33.3333f)
                verticalLineTo(22.6667f)
                curveTo(33.3333f, 22.313f, 33.1928f, 21.9739f, 32.9428f, 21.7239f)
                curveTo(32.6927f, 21.4738f, 32.3536f, 21.3333f, 32f, 21.3333f)
                curveTo(31.6464f, 21.3333f, 31.3072f, 21.4738f, 31.0572f, 21.7239f)
                curveTo(30.8071f, 21.9739f, 30.6666f, 22.313f, 30.6666f, 22.6667f)
                verticalLineTo(30.6667f)
                horizontalLineTo(22.6666f)
                curveTo(22.313f, 30.6667f, 21.9739f, 30.8071f, 21.7238f, 31.0572f)
                curveTo(21.4738f, 31.3072f, 21.3333f, 31.6464f, 21.3333f, 32f)
                curveTo(21.3333f, 32.3536f, 21.4738f, 32.6928f, 21.7238f, 32.9428f)
                curveTo(21.9739f, 33.1929f, 22.313f, 33.3333f, 22.6666f, 33.3333f)
                horizontalLineTo(30.6666f)
                verticalLineTo(41.3333f)
                curveTo(30.6666f, 41.687f, 30.8071f, 42.0261f, 31.0572f, 42.2761f)
                curveTo(31.3072f, 42.5262f, 31.6464f, 42.6667f, 32f, 42.6667f)
                curveTo(32.3536f, 42.6667f, 32.6927f, 42.5262f, 32.9428f, 42.2761f)
                curveTo(33.1928f, 42.0261f, 33.3333f, 41.687f, 33.3333f, 41.3333f)
                verticalLineTo(33.3333f)
                horizontalLineTo(41.3333f)
                curveTo(41.6869f, 33.3333f, 42.0261f, 33.1929f, 42.2761f, 32.9428f)
                curveTo(42.5262f, 32.6928f, 42.6666f, 32.3536f, 42.6666f, 32f)
                curveTo(42.6666f, 31.6464f, 42.5262f, 31.3072f, 42.2761f, 31.0572f)
                curveTo(42.0261f, 30.8071f, 41.6869f, 30.6667f, 41.3333f, 30.6667f)
                close()
            }
        }.build()
    }
}

@Composable
fun DotVector(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "vector",
            defaultWidth = 10.dp,
            defaultHeight = 10.dp,
            viewportWidth = 10f,
            viewportHeight = 10f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFA990DD)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10f, 5f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5f, 10f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 5f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10f, 5f)
                close()
            }
        }.build()
    }
}



