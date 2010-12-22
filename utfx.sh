#
# TODO: how to repackage saxon9he with RSA, etc. in the single jar
# TODO: TransformerFactoryImpl not found when using the -jar call. See:
# http://stackoverflow.com/questions/2018257/how-to-combine-library-with-my-jar
# Quoting above:
#   Executable JARs are launched with the "-jar" argument to the java
#   executable, and both the java "-cp" flag and the CLASSPATH environment
#   variable are ignored.

DIR=`dirname $0`

SAXON=$DIR/lib/saxon9he.jar
THIRD_PARTY_JARS=
TRANSFORM_FACTORY= #-Djavax.xml.transform.TransformerFactory=...
UTFX_FAT=$DIR/build/jar/utfxFat.jar
UTFX=.:$UTFX_FAT # adding '.' so utfx.properties and log4j.properties are found.

if [ ! -e $UTFX_FAT ] ; then
	cat <<END

$UTFX_FAT not yet built. Please run:

 ant fatjar
	
before trying again!
	
END
	exit 1
fi

if [ $# = 0 ] ; then
	cat <<END

please pass utfx test, either:
$0 -Dutfx.test.file=$DIR/samples/history/test/history_xhtml_test.xml
or:
$0 -Dutfx.test.dir=$DIR/samples/history

END
	exit 2
fi

java \
	-Dlog4j.configuration=file:$DIR/log4j.properties \
	$TRANSFORM_FACTORY \
	"$@" \
	-cp $DIR:$SAXON:$THIRD_PARTY_JARS:$UTFX \
	 utfx.runner.TestRunner utfx.framework.XSLTRegressionTest
