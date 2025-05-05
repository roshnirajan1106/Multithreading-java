Problem statement
- any single shared value can be read by multiple threads but write only by one thread
- no write possible , if reading is in progress
- no read possible , if writing is in progress

lock,await,unlock should always be in the same try,catch,finally block.
you should only unlock when we're done with the lock operation.
lock.lock();
try{
while(isWriting || readerCount >0){
condition.await(); -> if this reacquires the lock, it'll go again and check the while loop condition. especially useful when main write threads wake up. 
}
readerCount++;      
}
isWriting = true;
}catch (InterruptedException e) {
throw new RuntimeException(e);
}finally {
lock.unlock();
}
