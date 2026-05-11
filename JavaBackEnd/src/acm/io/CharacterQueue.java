package acm.io;

class CharacterQueue {
   private String buffer = "";
   private boolean isWaiting;

   public CharacterQueue() {
   }

   public synchronized void enqueue(char var1) {
      this.buffer = this.buffer + var1;
      this.notifyAll();
   }

   public synchronized void enqueue(String var1) {
      this.buffer = this.buffer + var1;
      this.notifyAll();
   }

   public synchronized char dequeue() {
      while(this.buffer.length() == 0) {
         try {
            this.isWaiting = true;
            this.wait();
            this.isWaiting = false;
         } catch (InterruptedException var2) {
         }
      }

      char var1 = this.buffer.charAt(0);
      this.buffer = this.buffer.substring(1);
      return var1;
   }

   public synchronized void clear() {
      this.buffer = "";
      this.notifyAll();
   }

   public boolean isWaiting() {
      return this.isWaiting;
   }
}
