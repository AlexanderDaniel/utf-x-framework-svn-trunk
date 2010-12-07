For pragmatic reasons this is a quick & dirty resurrection of the utf-x
framework. The original developers have responded to our requests but
unfortunately for the moment are too busy with other things.

I've done a minimalistic port to xslt2.0 (saxon9he), 
based on the svn trunk. 

My changes consist in replacing references to saxon9.jar and saxon9-dom.jar
to saxon9he.jar in build.xml, and in changing all exslt:node-set($X) to $X
according to Michael Kay's post:
http://sourceforge.net/tracker/index.php?func=detail&aid=2849653&group_id=29872&atid=397617
Additional changes were kindly provided by one of the original developers,
Alexander Daniel, which were necessary to pass the tests in the svn trunk.

After building the project you can use the script utfx.sh to run your own
tests, possibly adapting some environment variables in the script.


Here's the original utf-x README contents:


                               Unit Testing Framework - XSLT
                                    release 0.0.8 alpha
                                       26 August 2006

Getting started.
----------------

1. Read the UTF-X manual. You'll find a rendered version under dist/manual
    and the latest version at http://utf-x.sourceforge.net/manual/

2. Browse through three sample applications provided with this distribution.
    You will find these in the samples directory. Rendered versions of the samples
    (HTML and PDF) are in the rendered_samples directory. You can run the samples
    by executing the 'samples' ant target: ant samples.
    
We hope you'll enjoy test driven XSLT development with UTF-X.

UTF-X Development Team.
