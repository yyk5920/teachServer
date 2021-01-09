`timescale 1ns / 1ps
module alu_sim();
    reg [31:0] In1,In2;
    reg [3:0] ALUCtr;
    wire [31:0] Res;
    wire Zero;
    ALU u0(In1, In2, ALUCtr, Res, Zero);
    integer handle;
integer signalHandle;
initial begin
handle = $fopen("./result.txt");
signalHandle = $fopen("./signal.txt");
$fdisplay(handle,"time Res Zero ALUCtr In2 In1");
$fdisplay(signalHandle,"time Res Zero ALUCtr In2 In1");
end

initial
begin            
    $dumpfile("wave.vcd");        //生成的vcd文件名称
$dumpvars(0, alu_sim);
end
    initial
    begin
    In1=4;
    In2=8;
    ALUCtr=4'b0000;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0001;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0010;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0110;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0111;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    In1=32'h44444444;
    In2=32'h88888888;
    ALUCtr=4'b0000;
    #1;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0001;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0010;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0110;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0111;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    In1=32'h99999999;
    In2=32'h88888888;
    ALUCtr=4'b0000;
    #1;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0001;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0010;
    #0;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0110;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    ALUCtr=4'b0111;
    #10;
$fdisplay(handle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
$fdisplay(signalHandle,""%h %h %h %h %h %h",$time,Res,Zero,ALUCtr,In2,In1);
;
    $stop;
    end
endmodule