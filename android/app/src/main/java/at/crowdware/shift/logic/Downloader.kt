package at.crowdware.shift.logic

interface Downloader {
    fun downloadFile(url: String): Long
}