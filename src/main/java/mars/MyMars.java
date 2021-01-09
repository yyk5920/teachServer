package mars;
import com.yjs.mips.pojo.AnswerStatus;
import com.yjs.mips.pojo.MipsMemory;
import com.yjs.mips.pojo.MipsRegister;
import mars.util.*;
import mars.mips.hardware.*;
import java.io.*;
import java.util.*;

/*
Copyright (c) 2003-2012,  Pete Sanderson and Kenneth Vollmar

Developed by Pete Sanderson (psanderson@otterbein.edu)
and Kenneth Vollmar (kenvollmar@missouristate.edu)

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject
to the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

(MIT license, http://www.opensource.org/licenses/mit-license.html)
 */

/**
 * Launch the Mars application
 *
 * @author Pete Sanderson
 * @version December 2009
 **/

public class MyMars {

    /**
     * Main takes a number of command line arguments.<br>
     Usage:  Mars  [options] filename<br>
     Valid options (not case sensitive, separate by spaces) are:<br>
     a  -- assemble only, do not simulate<br>
     ad  -- both a and d<br>
     ae<n>  -- terminate MARS with integer exit code <n> if an assemble error occurs.<br>
     ascii  -- display memory or register contents interpreted as ASCII
     b  -- brief - do not display register/memory address along with contents<br>
     d  -- print debugging statements<br>
     da  -- both a and d<br>
     db  -- MIPS delayed branching is enabled.<br>
     dec  -- display memory or register contents in decimal.<br>
     dump  -- dump memory contents to file.  Option has 3 arguments, e.g. <br>
     <tt>dump &lt;segment&gt; &lt;format&gt; &lt;file&gt;</tt>.  Also supports<br>
     an address range (see <i>m-n</i> below).  Current supported <br>
     segments are <tt>.text</tt> and <tt>.data</tt>.  Current supported dump formats <br>
     are <tt>Binary</tt>, <tt>HexText</tt>, <tt>BinaryText</tt>.<br>
     h  -- display help.  Use by itself and with no filename</br>
     hex  -- display memory or register contents in hexadecimal (default)<br>
     ic  -- display count of MIPS basic instructions 'executed'");
     mc  -- set memory configuration.  Option has 1 argument, e.g.<br>
     <tt>mc &lt;config$gt;</tt>, where &lt;config$gt; is <tt>Default</tt><br>
     for the MARS default 32-bit address space, <tt>CompactDataAtZero</tt> for<br>
     a 32KB address space with data segment at address 0, or <tt>CompactTextAtZero</tt><br>
     for a 32KB address space with text segment at address 0.<br>
     me  -- display MARS messages to standard err instead of standard out. Can separate via redirection.</br>
     nc  -- do not display copyright notice (for cleaner redirected/piped output).</br>
     np  -- No Pseudo-instructions allowed ("ne" will work also).<br>
     p  -- Project mode - assemble all files in the same directory as given file.<br>
     se<n>  -- terminate MARS with integer exit code <n> if a simulation (run) error occurs.<br>
     sm  -- Start execution at Main - Execution will start at program statement globally labeled main.<br>
     smc  -- Self Modifying Code - Program can write and branch to either text or data segment<br>
     we  -- assembler Warnings will be considered Errors<br>
     <n>  -- where <n> is an integer maximum count of steps to simulate.<br>
     If 0, negative or not specified, there is no maximum.<br>
     $<reg>  -- where <reg> is number or name (e.g. 5, t3, f10) of register whose <br>
     content to display at end of run.  Option may be repeated.<br>
     <reg_name>  -- where <reg_name> is name (e.g. t3, f10) of register whose <br>
     content to display at end of run.  Option may be repeated. $ not required.<br>
     <m>-<n>  -- memory address range from <m> to <n> whose contents to<br>
     display at end of run. <m> and <n> may be hex or decimal,<br>
     <m> <= <n>, both must be on word boundary.  Option may be repeated.<br>
     pa  -- Program Arguments follow in a space-separated list.  This<br>
     option must be placed AFTER ALL FILE NAMES, because everything<br>
     that follows it is interpreted as a program argument to be<br>
     made available to the MIPS program at runtime.<br>
     **/



    private boolean simulate;
    private int displayFormat;
    private boolean verbose;  // display register name or address along with contents
    private boolean assembleProject; // assemble only the given file or all files in its directory
    private boolean pseudo;  // pseudo instructions allowed in source code or not.
    private boolean delayedBranching;  // MIPS delayed branching is enabled.
    private boolean warningsAreErrors; // Whether assembler warnings should be considered errors.
    private boolean startAtMain; // Whether to start execution at statement labeled 'main'
    private boolean countInstructions; // Whether to count and report number of instructions executed
    private boolean selfModifyingCode; // Whether to allow self-modifying code (e.g. write to text segment)
    private static final String rangeSeparator = "-";
    private static final int splashDuration = 2000; // time in MS to show splash screen
    private static final int memoryWordsPerLine = 4; // display 4 memory words, tab separated, per line
    private static final int DECIMAL = 0; // memory and register display format
    private static final int HEXADECIMAL = 1;// memory and register display format
    private static final int ASCII = 2;// memory and register display format
    private ArrayList registerDisplayList;
    private ArrayList memoryDisplayList;
    private ArrayList filenameList;
    private MIPSprogram code;
    private int maxSteps;
    private int instructionCount;
    private PrintStream out; // stream for display of command line output
    private ArrayList dumpTriples = null; // each element holds 3 arguments for dump option
    private ArrayList programArgumentList; // optional program args for MIPS program (becomes argc, argv)
    private int assembleErrorExitCode;  // MARS command exit code to return if assemble error occurs
    private int simulateErrorExitCode;// MARS command exit code to return if simulation error occurs

    public MyMars() {

        simulate = true;
        displayFormat = HEXADECIMAL;
        verbose = true;
        assembleProject = false;
        pseudo = true;
        delayedBranching = false;
        warningsAreErrors = false;
        startAtMain = false;
        countInstructions = false;
        selfModifyingCode = false;
        instructionCount = 0;
        assembleErrorExitCode = 0;
        simulateErrorExitCode = 0;
        registerDisplayList = new ArrayList();
        memoryDisplayList = new ArrayList();
        filenameList = new ArrayList();
        // do NOT use Globals.program for command line MARS -- it triggers 'backstep' log.
        code = new MIPSprogram();
        maxSteps = -1;
        out = System.out;
    }

    public AnswerStatus compile(String filename, List<MipsRegister> registers, List<MipsMemory> memories) {
//        System.out.println("这一句执行了"); // true
        Globals.initialize(false);
//        System.setProperty("java.awt.headless", "true");
        MemoryConfigurations.setCurrentConfiguration(MemoryConfigurations.getDefaultConfiguration());
        AnswerStatus status = new AnswerStatus();
        filenameList.add(filename);
        if (registers != null) {
            for (MipsRegister register : registers) {
                registerDisplayList.add(register.getName());
            }
        }
        if (memories != null) {
            for (MipsMemory memory : memories) {
                memoryDisplayList.add(memory.getAddress());
            }
        }

        Globals.getSettings().setBooleanSettingNonPersistent(Settings.DELAYED_BRANCHING_ENABLED, delayedBranching);
        Globals.getSettings().setBooleanSettingNonPersistent(Settings.SELF_MODIFYING_CODE_ENABLED, selfModifyingCode);
        ArrayList filesToAssemble = FilenameFinder.getFilenameList(filenameList, FilenameFinder.MATCH_ALL_EXTENSIONS);
        File mainFile = new File((String) filenameList.get(0)).getAbsoluteFile();// First file is "main" file

        List<Map<String, Integer>> ansReg;
        List<Map<String, Integer>> ansMem;

        try {
            ArrayList MIPSprogramsToAssemble =
                    code.prepareFilesForAssembly(filesToAssemble, mainFile.getAbsolutePath(), null);
            RegisterFile.initializeProgramCounter(startAtMain); // DPS 3/9/09
            ErrorList warnings = code.assemble(MIPSprogramsToAssemble, pseudo, warningsAreErrors);
            if (warnings != null && warnings.warningsOccurred()) {
                out.println(warnings.generateWarningReport());
            }
            code.simulate(maxSteps);

            ansReg = getRegisters(true);
        } catch (ProcessingException e) {
//            Globals.exitCode = assembleErrorExitCode;
//            Globals.exitCode = simulateErrorExitCode;
            status.setCorrect(false);
            status.setInfo(e.errors().generateErrorAndWarningReport());
            ansReg = getRegisters(false);
        } finally {
            ansMem = getMemories();
        }

        status.setReg(ansReg);
        status.setMem(ansMem);
        return status;
    }

    private List<Map<String, Integer>> getRegisters(boolean success) {
        List<Map<String, Integer>> result = new LinkedList<>();
        int value;  // handy local to use throughout the next couple loops
        Iterator regIter = registerDisplayList.iterator();
        while (regIter.hasNext()) {
            String reg = regIter.next().toString();
            value = RegisterFile.getUserRegister(reg).getValue();
            Map<String, Integer> map = new HashMap<>();
            if (success)
                map.put(reg, value);
            else
                map.put(reg, 0);
            result.add(map);
        }
        return result;
    }

    private List<Map<String, Integer>> getMemories() {
        List<Map<String, Integer>> result = new LinkedList<>();
        int value;
        Iterator memIter = memoryDisplayList.iterator();
        while (memIter.hasNext()) {
            int addr = Binary.stringToInt(memIter.next().toString());
            try {
                Map<String, Integer> map = new HashMap<>();
                if (Memory.inTextSegment(addr) || Memory.inKernelTextSegment(addr)) {
                    Integer iValue = Globals.memory.getRawWordOrNull(addr);
                    value = (iValue == null) ? 0 : iValue.intValue();
                } else {
                    value = Globals.memory.getWord(addr);
                }
                map.put(Binary.intToHexString(addr), value);
                result.add(map);

            } catch (AddressErrorException aee) {
                out.print("Invalid address: " + addr + "\t");
            }
        }
        return result;
    }
}