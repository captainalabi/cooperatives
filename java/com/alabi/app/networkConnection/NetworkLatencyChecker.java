package com.alabi.app.networkConnection;

import java.net.InetAddress;

import org.springframework.stereotype.Service;

@Service
public class NetworkLatencyChecker {

	public boolean isNetworkLatent(String ipAddress) {
		//String ipAddress = "google.com"; // Replace with the IP address or domain name of the server
		Boolean latency = false;
		try {
			InetAddress inetAddress = InetAddress.getByName(ipAddress);
			// Measure network latency
			Long startTime = System.nanoTime();
			Boolean reachable = inetAddress.isReachable(30000); // Timeout set to 5 seconds
			Long endTime = System.nanoTime();

			if (reachable) {
				long latencyInMilliseconds = (endTime - startTime) / 1_000_000; // Convert nanoseconds to milliseconds
				latency = false;
				System.out.println("Network latency to " + ipAddress + " is approximately " + latencyInMilliseconds
						+ " milliseconds.");
			} else {
				latency = true;
				System.out.println("Failed to reach " + ipAddress + ".");
			}
		} catch (Exception e) {
			System.out.println("Error occurred: Could not connect to the web address" + e.getMessage());
		}
		return latency;
	}
}
