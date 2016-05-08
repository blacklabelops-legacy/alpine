/**
 * Jenkins 2.0 Buildfile
 **/

 def getBranchName() {
   def branchName = env.JOB_NAME.replaceFirst('.+/', '')
   echo 'Building on Branch: ' + branchName
   def tagPostfix = ''
   if (branchName != null && !'master'.equals(branchName)) {
     tagPostfix = branchName
   }
   return tagPostfix
 }

 return this;
