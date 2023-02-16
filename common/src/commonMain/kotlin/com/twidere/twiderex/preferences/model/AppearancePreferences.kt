/*
 *  Twidere X
 *
 *  Copyright (C) TwidereProject and Contributors
 *
 *  This file is part of Twidere X.
 *
 *  Twidere X is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Twidere X is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Twidere X. If not, see <http://www.gnu.org/licenses/>.
 */
package com.twidere.twiderex.preferences.model

import kotlinx.serialization.Serializable

@Serializable
data class AppearancePreferences(
  val primaryColorIndex: Int = 0,
  val tabPosition: TabPosition = TabPosition.Bottom,
  val theme: Theme = Theme.Auto,
  val hideTabBarWhenScroll: Boolean = false,
  val hideFabWhenScroll: Boolean = false,
  val hideAppBarWhenScroll: Boolean = false,
  val isDarkModePureBlack: Boolean = false,
  val windowInfo: WindowInfo = WindowInfo(),
  val tabToTop: TabToTop = TabToTop.SingleTap
) {

  @Serializable
  data class WindowInfo(
    val top: Float = 50f,
    val start: Float = 50f,
    val width: Float = 400f,
    val height: Float = 800f,
  )

  @Serializable
  enum class TabPosition {
    Top,
    Bottom,
  }

  @Serializable
  enum class TabToTop {
    SingleTap,
    DoubleTap,
  }

  @Serializable
  enum class Theme {
    Auto,
    Light,
    Dark,
  }
}
