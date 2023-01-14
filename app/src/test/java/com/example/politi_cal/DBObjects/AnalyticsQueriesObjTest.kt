package com.example.politi_cal.DBObjects

import androidx.core.util.Supplier
import com.example.politi_cal.models.Celeb
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import kotlin.streams.toList

internal class AnalyticsQueriesObjTest {
    fun getCelebList(): ArrayList<Celeb> {
        var celebList = ArrayList<Celeb>();
        var noVoteCeleb = Celeb(
            "Maccabi Haifa", "Dolev", "Haziza",
            19991222, "", "", "Sports", 0, 0
        )
        var totalLeftCeleb = Celeb(
            "Famous", "Moshe", "Peretz",
            19841012, "", "", "Independant",
            0, 100
        )
        var totalRightCeleb = Celeb(
            "Maccabi Haifa", "Neta",
            "Lavi", 19920415, "", "",
            "Sports", 100, 0
        )
        var invalidVotesCeleb = Celeb(
            "Maccabi Haifa", "Shon",
            "Goldberg", 19920415, "", "",
            "Sports", -8, 0
        )
        var celeb1 = Celeb(
            "Maccabi Tel Aviv", "Oscar", "Gloch",
            20000320, "", "", "Sports", 40, 60
        )
        var celeb2 = Celeb(
            "Hapoel Beer Sheva", "Ariel", "Harush",
            19890516, "", "", "Sports", 59, 32
        )
        return arrayListOf<Celeb>(
            noVoteCeleb, totalLeftCeleb, totalRightCeleb, invalidVotesCeleb,
            celeb1, celeb2
        )
    }

    fun getCelebDistributionTest(celeb: Celeb): ArrayList<Double> {
        return if (celeb.LeftVotes < 0 || celeb.RightVotes < 0) {
            throw ArithmeticException("Number of votes cannot be negative")
        } else if (celeb.LeftVotes == celeb.RightVotes) {
            arrayListOf<Double>(50.0, 50.0)
        } else {
            val left = celeb.LeftVotes
            val right = celeb.RightVotes
            val total = left + right
            val left_distribution = (left / total).toDouble()
            val right_distribution = (right / total).toDouble()
            arrayListOf(left_distribution * 100, right_distribution * 100)
        }
    }

    /**
     * Celeb distribution cases:
     * 1. Empty case: The celebrity have no vote, and an arithmetic exception will not get thrown
     * 2. Total left case: The celebrity will have only left votes
     * 3. Total right case: The celebrity will have only right votes
     * 4. Invalid celeb case: The celebrity will have negative amount of votes and an exception
     *    will be thrown.
     */

    @Test
    fun getCelebDistributionNoVotes() {
        val celebList = getCelebList()
        val distribution = getCelebDistributionTest(celebList[0])
        val function: () -> ArrayList<Double> = { getCelebDistributionTest(celebList[0]) }
        assertDoesNotThrow(function)
        val expected_left = 50.0
        val expected_right = 50.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    @Test
    fun getCelebDistributionTotalLeft() {
        val celebList = getCelebList()
        val distribution = getCelebDistributionTest(celebList[1])
        val expected_left = 100.0
        val expected_right = 0.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    @Test
    fun getCelebDistributionTotalRight() {
        val celebList = getCelebList()
        val distribution = getCelebDistributionTest(celebList[2])
        val expected_left = 0.0
        val expected_right = 100.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    @Test
    fun getCelebDistributionInvalid() {
        val celebList = getCelebList()
        assertThrows(ArithmeticException::class.java) {
            getCelebDistributionTest(celebList[3])
        }
    }

    /**
     * Company distribution cases:
     * 1. No celebs cases: The distribution will be 50-50
     * 2. Multiple celebs case
     */

    fun getCompanyDistributionTest(companyName: String): ArrayList<Double> {
        var celebList = getCelebList()
        celebList.removeAt(3)
        var list = celebList.stream().filter { it.Company == companyName }.toList()
        if (list.isEmpty()) {
            return arrayListOf(50.0, 50.0)
        }

        var left: Double = 0.0
        var right: Double = 0.0
        var i = 0
        while (i < list.size) {
            if (list[i].RightVotes > list[i].LeftVotes) {
                right += 1
            } else if (list[i].RightVotes < list[i].LeftVotes) {
                left += 1
            } else {
                right += 0.5
                left += 0.5
            }
            i += 1
        }
        var total: Double = left + right
        val left_distribution = (left / total).toDouble()
        val right_distribution = (right / total).toDouble()
        return arrayListOf(left_distribution * 100, right_distribution * 100)

    }

    @Test
    fun getCompanyDistributionNoCelebs() {
        val distribution = getCompanyDistributionTest("Maccabi Petah Tikva")
        val expected_left = 50.0
        val expected_right = 50.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    @Test
    fun getCompanyDistributionMultipleCelebs() {
        val distribution = getCompanyDistributionTest("Maccabi Haifa")
        val expected_left = 25.0
        val expected_right = 75.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    /**
     * Category distribution cases:
     * 1. No companies case
     * 2. Multiple companies case
     */

    fun getCategoryDistributionTest(category: String): ArrayList<Double> {
        var celebList = getCelebList()
        celebList.removeAt(3)
        var list = celebList.stream().filter { it.Category == category }.toList()
        if (list.isEmpty()) {
            return arrayListOf(50.0, 50.0)
        }
        var companies = list.stream().map { it.Company }.toList()
        var companiesList = ArrayList<String>()
        for (company in companies) {
            if (companiesList.contains(company)) {
                continue
            }
            companiesList.add(company)
        }
        var left: Double = 0.0
        var right: Double = 0.0
        var i = 0
        while (i < companiesList.size) {
            var distribution = getCompanyDistributionTest(companiesList[i])
            var temp_left = distribution[0]
            var temp_right = distribution[1]
            if (temp_right > temp_left) {
                right += 1
            } else if (temp_right < temp_left) {
                left += 1
            } else {
                right += 0.5
                left += 0.5
            }
            i += 1
        }
        var total: Double = left + right
        val left_distribution = (left / total).toDouble()
        val right_distribution = (right / total).toDouble()
        return arrayListOf(left_distribution * 100, right_distribution * 100)
    }

    @Test
    fun getCategoryStatisticsNoCompanies() {
        val distribution = getCategoryDistributionTest("Journalism")
        val expected_left = 50.0
        val expected_right = 50.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    @Test
    fun getCategoryStatisticsMultipleCompanies() {
        val distribution = getCategoryDistributionTest("Sports")
        val expected_left = (100.0 / 3.0).toFloat()
        val expected_right = 2 * (100.0 / 3.0).toFloat()
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }

    fun getTotalDistributionTest(): ArrayList<Double> {
        var celebList = getCelebList()
        celebList.removeAt(3)
        var left = 0.0
        var right = 0.0
        for (celeb in celebList) {
            if (celeb.RightVotes > celeb.LeftVotes) {
                right += 1
            } else if (celeb.RightVotes < celeb.LeftVotes) {
                left += 1
            } else {
                right += 0.5
                left += 0.5
            }
        }
        var total = left + right
        val left_distribution = (left / total).toDouble()
        val right_distribution = (right / total).toDouble()
        return arrayListOf(left_distribution * 100, right_distribution * 100)
    }

    @Test
    fun getTotalDistribution() {
        val distribution = getTotalDistributionTest()
        val expected_left = 50.0
        val expected_right = 50.0
        val actual_left = distribution[0]
        val actual_right = distribution[1]
        assertEquals(expected_left.toFloat(), actual_left.toFloat())
        assertEquals(expected_right.toFloat(), actual_right.toFloat())
        assertEquals(100.0, actual_left + actual_right, 0.00001)
    }
}