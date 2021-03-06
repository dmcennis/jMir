/*
 * VariabilityOfNotePrevalenceOfUnpitchedInstrumentsFeature.java
 * Version 1.2
 *
 * Last modified on April 11, 2010.
 * McGill University
 */

package jsymbolic.features;

import java.util.LinkedList;
import javax.sound.midi.*;
import ace.datatypes.FeatureDefinition;
import jsymbolic.processing.MIDIIntermediateRepresentations;


/**
 * A feature exractor that finds the standard deviation of the fraction of Note
 * Ons played by each (unpitched) MIDI Percussion Key Map instrument that is 
 * used to play at least one note. It should be noted that only instruments 35 
 * to 81 are included here, as they are the ones that are included in the 
 * official standard.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class VariabilityOfNotePrevalenceOfUnpitchedInstrumentsFeature
     extends MIDIFeatureExtractor
{
     /* CONSTRUCTOR ***********************************************************/
     
     
     /**
      * Basic constructor that sets the definition and dependencies (and their
      * offsets) of this feature.
      */
     public VariabilityOfNotePrevalenceOfUnpitchedInstrumentsFeature()
     {
          String name = "Variability of Note Prevalence of Unpitched Instruments";
          String description = "Standard deviation of the fraction of Note Ons played by\n" +
               "each (unpitched) MIDI Percussion Key Map instrument that\n" +
               "is used to play at least one note.\n" +
               "\nIt should be noted that only instruments 35 to 81 are\n" +
               "included here, as they are the ones that are included in\n" +
               "the official standard.";
          boolean is_sequential = true;
          int dimensions = 1;
          definition = new FeatureDefinition( name,
               description,
               is_sequential,
               dimensions );
          
          dependencies = null;
          
          offsets = null;
     }
     
     
     /* PUBLIC METHODS ********************************************************/
     
     
     /**
      * Extracts this feature from the given MIDI sequence given the other
      * feature values.
      *
      * <p>In the case of this feature, the other_feature_values parameters
      * are ignored.
      *
      * @param sequence			The MIDI sequence to extract the feature
      *                                 from.
      * @param sequence_info		Additional data about the MIDI sequence.
      * @param other_feature_values	The values of other features that are
      *					needed to calculate this value. The
      *					order and offsets of these features
      *					must be the same as those returned by
      *					this class's getDependencies and
      *					getDependencyOffsets methods
      *                                 respectively. The first indice indicates
      *                                 the feature/window and the second
      *                                 indicates the value.
      * @return				The extracted feature value(s).
      * @throws Exception		Throws an informative exception if the
      *					feature cannot be calculated.
      */
     public double[] extractFeature( Sequence sequence,
          MIDIIntermediateRepresentations sequence_info,
          double[][] other_feature_values )
          throws Exception
     {
          double value;
          if (sequence_info != null)
          {
               // Find the number of unpitched instruments used to play at least one note
               int instruments_present = 0;
               for (int instrument = 35; instrument < 82; instrument++)
                    if (sequence_info.non_pitched_instrumentation_frequencies[instrument] != 0)
                         instruments_present++;
               
               // Calculate the feature value
               double[] instrument_frequencies = new double[instruments_present];
               int count = 0;
               for (int instrument = 35; instrument < 82; instrument++)
                    if (sequence_info.non_pitched_instrumentation_frequencies[instrument] != 0)
                    {
                    instrument_frequencies[count] = (double) sequence_info.non_pitched_instrumentation_frequencies[instrument];
                    count++;
                    }
               value = mckay.utilities.staticlibraries.MathAndStatsMethods.getStandardDeviation(instrument_frequencies);
          }
          else value = -1.0;
          
          double[] result = new double[1];
          result[0] = value;
          return result;
     }
}