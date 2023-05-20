/****************************************************************************
* Copyright (C) 2023 CrowdWare
*
* This file is part of SHIFT.
*
*  SHIFT is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
*
*  SHIFT is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  You should have received a copy of the GNU General Public License
*  along with SHIFT.  If not, see <http://www.gnu.org/licenses/>.
*
****************************************************************************/
package at.crowdware.shift.logic

import java.io.Serializable
import nl.tudelft.ipv8.keyvault.PrivateKey

data class Account(
    val name: String = "",
    val uuid: String = "",
    val ruuid: String = "",
    val country: String = "",
    val language: String = "",
    var scooping: ULong = 0u,
    var isScooping: Boolean = false,
    var level_1_count: UInt = 0u,
    var level_2_count: UInt = 0u,
    var level_3_count: UInt = 0u,
    var privateKey: String = "",
    var transactions: MutableList<Transaction> = mutableListOf<Transaction>()
) : Serializable
