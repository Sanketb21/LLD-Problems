package RealTimeChatRoomUserTracker;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ChatRoomTracker {
	private final Map<String, Set<String>> rooms = new ConcurrentHashMap<>();
	
	public void joinRoom(String room, String user) {
		if(!rooms.containsKey(room)) rooms.put(room, new HashSet<>());
		rooms.get(room).add(user);
	}
	
	public void leaveRoom(String room, String user) {
		Set<String> users = rooms.get(room);
		if(users != null) {
			users.remove(user);
			
			if(users.isEmpty()) {
				rooms.remove(room);
			}
		}
	}
	
	public List<String> getActiveUsers(String room){
		Set<String> users = rooms.getOrDefault(room, new HashSet<>());
		return users.stream().sorted().collect(Collectors.toList());
	}
	
	public long getUsersCount(String room) {
		Set<String> users = rooms.getOrDefault(room, new HashSet<>());
		return users.stream().count();
	}
	

	public static void main(String[] args) throws InterruptedException{
		// TODO Auto-generated method stub
		ChatRoomTracker cr = new ChatRoomTracker();
		int threadCount = 100;
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		
		CountDownLatch latch = new CountDownLatch(1); // This latch makes all threads wait until they are all ready to "race"
		
		for(int i = 0; i < threadCount; i++) {
			final int userId = i;
			executor.execute(() -> {
				try {
					latch.await();
					
					cr.joinRoom("General", "User" + userId);
					cr.joinRoom("Tech", "User" + userId);
					cr.joinRoom("General", "User" + userId);
					
					// Simulate some users leaving
					if(userId % 2 == 0) {
						cr.leaveRoom("General", "User" + userId);
					}
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			});
		}
		
		latch.countDown(); //Race begins
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
		
		List<String> generalUsers = cr.getActiveUsers("General");
        System.out.println("General Room Count: " + generalUsers.size() + " (Expected: 50)");
        
        List<String> techUsers = cr.getActiveUsers("Tech");
        System.out.println("Tech Room Count: " + techUsers.size() + " (Expected: 100)");

        System.out.println("First 3 users in Tech (Sorted): " + techUsers.subList(0, 3));
        
        boolean hasDuplicates = techUsers.size() != techUsers.stream().distinct().count();
        System.out.println("Has duplicates in Tech? " + hasDuplicates + " (Expected: false)");
		
	}

}
