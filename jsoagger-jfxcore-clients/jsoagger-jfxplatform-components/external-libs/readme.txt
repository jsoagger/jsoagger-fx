mvn install:install-file -Dfile=....external-libs/gson-2.8.6-local.jar -DgroupId=com.google.code.gson -DartifactId=gson -Dversion=2.8.6 -Dpackaging=jar

https://devcenter.heroku.com/articles/local-maven-dependencies

mvn deploy:deploy-file -Durl=file:///Users/vonji/git/jsoagger-fx/jsoagger-jfxcore-clients/jsoagger-jfx-demo/jsoagger-cloudstub-operations/external-libs/ -Dfile=gson-2.8.6-local.jar -DgroupId=com.google.code.gson -DartifactId=gson -Dversion=2.8.6 -Dpackaging=jar